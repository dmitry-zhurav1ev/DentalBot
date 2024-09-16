package com.project._group.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@AllArgsConstructor
@Configuration
public class RandomConfig {

    @Bean
    public Random random() {
        return new Random();
    }
}
