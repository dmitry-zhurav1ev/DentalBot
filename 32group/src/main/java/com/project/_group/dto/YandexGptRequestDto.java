package com.project._group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class YandexGptRequestDto {

    private String modelUri;

    private CompletionOptions completionOptions;

    private List<Message> messages;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class CompletionOptions {
        private Boolean stream;

        private Double temperature;

        private String maxTokens;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Message {
        private String role;

        private String text;
    }
}

