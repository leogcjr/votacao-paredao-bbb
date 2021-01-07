package com.globo.votacaoparedao.entity;

import java.util.Objects;

public class Voto {
    private String cpf;
    private Pergunta answer;

    public Voto(String cpf, Pergunta answer) {
        this.cpf = cpf;
        this.answer = answer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto vote = (Voto) o;
        return Objects.equals(cpf, vote.cpf) &&
                answer == vote.answer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, answer);
    }
}
