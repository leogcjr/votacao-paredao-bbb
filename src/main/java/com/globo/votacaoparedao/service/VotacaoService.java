package com.globo.votacaoparedao.service;

import java.util.List;

import com.globo.votacaoparedao.dto.*;

public interface VotacaoService {
    List<VotacaoResponseDto> listVotacoes();
    VotacaoResponseDto getVotacao(String id);
    VotacaoResponseDto createVoting(VotacaoRequestDto dto);
    VotoResponseDto addVoto(VotoRequestDto dto);
    VotacaoResultadoResponseDto getResultadoVotacao(String id);
}
