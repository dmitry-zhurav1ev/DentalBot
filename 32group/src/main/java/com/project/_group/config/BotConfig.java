package com.project._group.config;

import com.project._group.bot.DentalBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(DentalBot bot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
            return api;
    }
}
