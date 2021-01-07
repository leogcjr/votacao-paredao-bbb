package com.globo.votacaoparedao.dto;

import com.globo.votacaoparedao.entity.ContadorVotos;

public class VotacaoResultadoResponseDto {
    private ParedaoResponseDto agenda;
    private ContadorVotos voteCount;

    public ParedaoResponseDto getAgenda() {
        return agenda;
    }

    public void setAgenda(ParedaoResponseDto agenda) {
        this.agenda = agenda;
    }

    public ContadorVotos getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(ContadorVotos voteCount) {
        this.voteCount = voteCount;
    }
}
