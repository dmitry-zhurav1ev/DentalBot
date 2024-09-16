package com.project._group.client.impl;

import com.project._group.client.YandexGptClient;
import com.project._group.config.YandexGptConfig;
import com.project._group.dto.YandexGptRequestDto;
import com.project._group.dto.YandexGptResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class YandexGptClientImpl implements YandexGptClient {

    private final YandexGptConfig yandexGPTConfig;
    private final WebClient webClient;


    public YandexGptResponseDto yandexGptAssessment(String result) {
        YandexGptRequestDto yandexGPTRequestDTO =
                YandexGptRequestDto.builder()
                        .modelUri(
                                yandexGPTConfig.getModelUriP1()
                                        + yandexGPTConfig.getCatalogId()
                                        + yandexGPTConfig.getModelUriP2())
                        .completionOptions(
                                YandexGptRequestDto.CompletionOptions.builder().stream(false)
                                        .temperature(Double.parseDouble(yandexGPTConfig.getTemperature()))
                                        .maxTokens(yandexGPTConfig.getMaxTokens())
                                        .build())
                        .messages(
                                List.of(
                                        YandexGptRequestDto.Message.builder()
                                                .role(yandexGPTConfig.getSystemRole())
                                                .text(yandexGPTConfig.getSystemText())
                                                .build(),
                                        YandexGptRequestDto.Message.builder()
                                                .role(yandexGPTConfig.getUserRole())
                                                .text(yandexGPTConfig.getUserQuestionPrompt()+result)
                                                .build()))
                        .build();

        Mono<YandexGptResponseDto> response =
                webClient
                        .post()
                        .bodyValue(yandexGPTRequestDTO)
                        .retrieve()
                        .bodyToMono(YandexGptResponseDto.class);
        YandexGptResponseDto responseDTO = response.block();
        return responseDTO;
    }
}
