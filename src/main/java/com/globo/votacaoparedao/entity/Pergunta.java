package com.globo.votacaoparedao.entity;

public enum Pergunta {

    NO("Não"),
    YES("Sim");

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
