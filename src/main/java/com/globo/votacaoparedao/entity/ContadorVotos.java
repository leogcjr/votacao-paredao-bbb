package com.globo.votacaoparedao.entity;

public class ContadorVotos {
    private Long candidato_A;
    private Long candidato_B;

    public ContadorVotos(Long candidato_A, Long no) {
        this.candidato_A = candidato_A;
        this.candidato_B = no;
    }

    public Long getCandidato_A() {
        return candidato_A;
    }

    public void setCandidato_A(Long yes) {
        this.candidato_A = yes;
    }

    public Long getCandidato_B() {
        return candidato_B;
    }

    public void setCandidato_B(Long no) {
        this.candidato_B = no;
    }
}
