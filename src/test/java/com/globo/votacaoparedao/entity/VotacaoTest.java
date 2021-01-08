package com.globo.votacaoparedao.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.globo.votacaoparedao.entity.Pergunta;
import com.globo.votacaoparedao.entity.Votacao;
import com.globo.votacaoparedao.entity.Voto;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class VotacaoTest {

    @Test
    public void shouldReturnTrueIfExpired() {
        Votacao voting = new Votacao();
        voting.setExpirationDate(Instant.now().minusSeconds(30));

        assertTrue(voting.isExpired());
    }

    @Test
    public void shouldReturnFalseIfNotExpired() {
        Votacao voting = new Votacao();
        voting.setExpirationDate(Instant.now().plusSeconds(30));

        assertFalse(voting.isExpired());
    }

    @Test
    public void shouldValidateCpfAlreadyVoted(){
        Votacao voting = new Votacao();

        voting.addVote(new Voto("111", Pergunta.Canditado_B));
        voting.addVote(new Voto("222", Pergunta.Canditado_A));
        voting.addVote(new Voto("111", Pergunta.Canditado_A));

        assertTrue(voting.validaVotoPorMinuto("111"));
    }

}