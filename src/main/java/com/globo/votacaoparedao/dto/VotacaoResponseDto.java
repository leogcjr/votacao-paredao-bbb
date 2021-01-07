package com.globo.votacaoparedao.dto;

import java.time.Instant;
import java.util.List;

import com.globo.votacaoparedao.entity.Voto;

public class VotacaoResponseDto {

    private String id;

    private ParedaoResponseDto agenda;

    private Integer minutesToExpiration;

    private Instant expirationDate;

    private List<Voto> votes;

    private boolean closed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParedaoResponseDto getAgenda() {
        return agenda;
    }

    public void setAgenda(ParedaoResponseDto agenda) {
        this.agenda = agenda;
    }

    public Integer getMinutesToExpiration() {
        return minutesToExpiration;
    }

    public void setMinutesToExpiration(Integer minutesToExpiration) {
        this.minutesToExpiration = minutesToExpiration;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Voto> getVotes() {
        return votes;
    }

    public void setVotes(List<Voto> votes) {
        this.votes = votes;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
