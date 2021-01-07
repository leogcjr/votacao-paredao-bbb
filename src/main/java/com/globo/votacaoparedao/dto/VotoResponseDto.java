package com.globo.votacaoparedao.dto;

public class VotoResponseDto {
    private boolean success;

    public VotoResponseDto(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
