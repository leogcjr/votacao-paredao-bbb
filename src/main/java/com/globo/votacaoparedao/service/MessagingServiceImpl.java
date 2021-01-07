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
        producer.send(String.format("Agenda '%s' closed! Votes: [Yes= %d] ~ [No= %d]",
                votingResult.getAgenda().getName(),
                votingResult.getVoteCount().getYes(),
                votingResult.getVoteCount().getNo()
        ));
    }
}
