package com.globo.votacaoparedao.dto;

import com.globo.votacaoparedao.entity.Pergunta;

public class VotoRequestDto {
    private String votingId;
    private String cpf;
    private Pergunta answer;

    public String getVotingId() {
        return votingId;
    }

    public void setVotingId(String votingId) {
        this.votingId = votingId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Pergunta getAnswer() {
        return answer;
    }

    public void setAnswer(Pergunta answer) {
        this.answer = answer;
    }
}
