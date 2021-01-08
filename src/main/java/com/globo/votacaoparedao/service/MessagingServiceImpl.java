package com.globo.votacaoparedao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globo.votacaoparedao.dto.VotacaoResultadoResponseDto;
import com.globo.votacaoparedao.kafka.Producer;

@Service
public class MessagingServiceImpl implements MessagingService {

    private final Producer producer;

    @Autowired
    public MessagingServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void send(VotacaoResultadoResponseDto votingResult) {
        producer.send(String.format("Paredao '%s' Finalizado! Votos: [Canditado A = %d] ~ [Canditado B = %d]",
                votingResult.getAgenda().getName(),
                votingResult.getVoteCount().getCandidato_A(),
                votingResult.getVoteCount().getCandidato_B()
        ));
    }
}
