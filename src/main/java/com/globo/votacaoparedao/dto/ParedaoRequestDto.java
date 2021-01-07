package com.globo.votacaoparedao.dto;

public class ParedaoRequestDto {
    private String name;

    public ParedaoRequestDto(){

    }

    public ParedaoRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
