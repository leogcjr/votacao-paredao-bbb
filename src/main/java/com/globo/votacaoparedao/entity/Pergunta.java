package com.globo.votacaoparedao.entity;

public enum Pergunta {

    Canditado_A("Canditado A"),
    Canditado_B("Canditado B");

    private String answer;

    Pergunta(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
