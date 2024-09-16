package com.project._group.service;

import com.project._group.dto.NeuralResponseDto;

public interface NeuralDiagnosticService {
    NeuralResponseDto sendNeuralRequest(String fileUrl);
}
