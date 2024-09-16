package com.project._group.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yagpt")
@Getter
@Setter
public class YandexGptConfig {

    private String url;

    private String apiToken;

    private String catalogId;

    private String modelUriP1;

    private String modelUriP2;

    private String systemText;

    private String userQuestionPrompt;

    private String temperature;

    private String maxTokens;

    private String systemRole;

    private String userRole;

}
