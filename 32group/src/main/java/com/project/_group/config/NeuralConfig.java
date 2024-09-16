package com.project._group.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("neural")
@Getter
@Setter
public class NeuralConfig {
    private String url;
}
