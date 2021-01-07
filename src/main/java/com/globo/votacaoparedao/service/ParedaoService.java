package com.globo.votacaoparedao.service;

import java.util.List;

import com.globo.votacaoparedao.dto.ParedaoRequestDto;
import com.globo.votacaoparedao.dto.ParedaoResponseDto;

public interface ParedaoService {
    List<ParedaoResponseDto> listParedaos();
    ParedaoResponseDto getParedao(String id);
    ParedaoResponseDto createParedao(ParedaoRequestDto dto);
}
