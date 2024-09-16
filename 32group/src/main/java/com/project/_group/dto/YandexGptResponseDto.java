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
public class YandexGptResponseDto {
    private Result result;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Result {
        private List<Alternative> alternatives;

        private Usage usage;

        private String modelVersion;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class Alternative {
            private Message message;

            private String status;

            @NoArgsConstructor
            @AllArgsConstructor
            @Data
            @Builder
            public static class Message {
                private String role;

                private String text;
            }
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class Usage {
            private String inputTextTokens;

            private String completionTokens;

            private String totalTokens;
        }
    }
}
