package com.project._group.client;

import com.project._group.dto.YandexGptRequestDto;
import com.project._group.dto.YandexGptResponseDto;

public interface YandexGptClient {
    YandexGptResponseDto yandexGptAssessment(String res);
}
