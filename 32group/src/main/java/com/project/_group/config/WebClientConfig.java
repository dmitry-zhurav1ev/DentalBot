package com.project._group.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {
    private YandexGptConfig yandexGPTConfig;

    @Bean
    public WebClient yandexWebClient() {
        return WebClient.builder()
                .baseUrl(yandexGPTConfig.getUrl())
                .defaultHeader("Authorization", "Api-Key " + yandexGPTConfig.getApiToken())
                .build();
    }
}
