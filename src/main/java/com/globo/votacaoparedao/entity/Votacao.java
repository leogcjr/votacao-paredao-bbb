package com.globo.votacaoparedao.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "votacao")
public class Votacao {

    @Id
    private ObjectId id;

    private Paredao paredao;

    private Integer minutesToExpiration;

    private Instant expirationDate;

    private List<Voto> votes;

    private boolean closed;

    public Votacao() {
        this.votes = new ArrayList<>();
    }

    public Votacao(Paredao agenda, Integer minutesToExpiration) {
        this.paredao = agenda;
        this.minutesToExpiration = minutesToExpiration;
        this.expirationDate = Instant.now().plusSeconds(minutesToExpiration * 60);
        this.votes = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Paredao getParedao() {
        return paredao;
    }

    public void setParedao(Paredao paredao) {
        this.paredao = paredao;
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

    public void addVote(Voto vote) {
        votes.add(vote);
    }

    public boolean isExpired(){
        return this.getExpirationDate().isBefore(Instant.now());
    }

    public boolean validaVotoPorMinuto(String cpf){
        return this.votes.stream().anyMatch(vote -> vote.getCpf().equals(cpf));
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Votacao voting = (Votacao) o;
        return Objects.equals(id, voting.id) &&
                Objects.equals(minutesToExpiration, voting.minutesToExpiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minutesToExpiration);
    }
}
