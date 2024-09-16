package com.project._group.service.impl;

import com.project._group.config.NeuralConfig;
import com.project._group.dto.NeuralRequestDto;
import com.project._group.dto.NeuralResponseDto;
import com.project._group.service.NeuralDiagnosticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class NeuralDiagnosticServiceImpl implements NeuralDiagnosticService {

    private final RestTemplate restTemplate;

    private final NeuralConfig config;

    @Override
    public NeuralResponseDto sendNeuralRequest(String fileUrl) {
        NeuralRequestDto neuralRequestDto = new NeuralRequestDto(fileUrl);
        NeuralResponseDto responseDto = restTemplate.postForObject(config.getUrl(), neuralRequestDto, NeuralResponseDto.class);
        return responseDto;
    }
}
