package com.globo.votacaoparedao.api;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.globo.votacaoparedao.api.VotacaoController;
import com.globo.votacaoparedao.dto.*;
import com.globo.votacaoparedao.entity.ContadorVotos;
import com.globo.votacaoparedao.service.VotacaoService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class VotacaoControllerTest {

    @Mock
    public VotacaoService votingService;

    @InjectMocks
    public VotacaoController votingController;

    @Test
    public void shouldReturnZeroVotings() {
        ResponseEntity<?> resp = votingController.getAll();

        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(0, ((LinkedList<?>) resp.getBody()).size());
    }

    @Test
    public void shouldReturnVotings() {
        List<VotacaoResponseDto> listDto = new ArrayList<>();
        VotacaoResponseDto voting = new VotacaoResponseDto();
        listDto.add(voting);

        Mockito.when(votingService.listVotacoes()).thenReturn(listDto);

        ResponseEntity<?> resp = votingController.getAll();

        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) resp.getBody()).size());
    }

    @Test
    public void shouldCreateVoting() throws URISyntaxException {
        VotacaoRequestDto req = new VotacaoRequestDto();
        VotacaoResponseDto resp = new VotacaoResponseDto();

        resp.setId(new ObjectId().toHexString());
        Mockito.when(votingService.createVoting(req)).thenReturn(resp);

        ResponseEntity<?> response = votingController.create(req);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldVote() throws URISyntaxException {
        VotoRequestDto req = new VotoRequestDto();
        VotoResponseDto resp = new VotoResponseDto(true);

        Mockito.when(votingService.addVoto(req)).thenReturn(resp);

        ResponseEntity<?> response = votingController.vote(req);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldGetVotingResult(){
        String id = new ObjectId().toHexString();
        VotacaoResultadoResponseDto resp = new VotacaoResultadoResponseDto();
        resp.setAgenda(new ParedaoResponseDto(id, "test"));
        resp.setVoteCount(new ContadorVotos(1L,2L));

        Mockito.when(votingService.getResultadoVotacao(id)).thenReturn(resp);

        ResponseEntity<?> response = votingController.getResultadoVotacao(id);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}