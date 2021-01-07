package com.globo.votacaoparedao.service;

import com.globo.votacaoparedao.dto.VotacaoResultadoResponseDto;

public interface MessagingService {
    void send(VotacaoResultadoResponseDto votingResult);
}
