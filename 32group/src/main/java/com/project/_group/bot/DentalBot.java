package com.project._group.bot;

import com.project._group.client.YandexGptClient;
import com.project._group.dto.NeuralResponseDto;
import com.project._group.service.NeuralDiagnosticService;
import com.project._group.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class DentalBot extends TelegramLongPollingBot {

    @Autowired
    S3Service s3;
    @Autowired
    NeuralDiagnosticService diagnosticService;
    @Autowired
    YandexGptClient gptClient;
    @Autowired
    Random random;

    public DentalBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                Message message = update.getMessage();
                startMessage(message.getChatId());
            }else{
                SendMessage(update.getMessage().getChatId(),"Пришли фото зубов");
            }
        } if (update.hasMessage() && update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String photoId = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).map(PhotoSize::getFileId).orElse("");
            log.info("PhotoId: {}", photoId);
            String url = "";
            String requestUrl = "";
            if (!photoId.isEmpty()) {
                try {

                    File file = execute(new GetFile(photoId));

                    String fileUrl = getFileUrl(file.getFilePath());

                    int num = random.nextInt();

                    downloadFile(fileUrl, "D:\\photo"+num+".jpg");
                     url = "D:\\photo"+num+".jpg";
                     requestUrl = "photo"+num+".jpg";

                } catch (TelegramApiException | IOException e) {
                    log.info(e.getMessage());
                }
            }
            s3.uploadFileToS3(url);
            NeuralResponseDto responseDto = diagnosticService.sendNeuralRequest(requestUrl);
            log.info("Response: {}", responseDto.getResult());
            SendMessage(update.getMessage().getChatId(), "Ваш вид прикуса: " + responseDto.getResult()+ "\n\n" +
                    gptClient.yandexGptAssessment(responseDto.getResult())
                    .getResult()
                    .getAlternatives()
                    .getFirst()
                    .getMessage()
                    .getText());
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "Dental bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private String getFileUrl(String filePath) {
        return "https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath;
    }

    // Метод для скачивания файла
    private void downloadFile(String url, String destination) throws IOException {
        try (InputStream in = new java.net.URL(url).openStream()) {
            Files.copy(in, Paths.get(destination));
        }
    }

    private void SendMessage(Long chatId, String text){
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try{
            execute(sendMessage);
        }catch(TelegramApiException e){
            log.error("Ошибка отправки сообщения", e);
        }
    }

    public void startMessage(Long chatId) {
        String text = "Отправь фото и получи консультацию по прикусу!";
        SendMessage(chatId, text);
    }

}
