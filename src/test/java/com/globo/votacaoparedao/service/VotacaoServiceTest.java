package com.globo.votacaoparedao.service;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.globo.votacaoparedao.dto.*;
import com.globo.votacaoparedao.entity.Paredao;
import com.globo.votacaoparedao.entity.Pergunta;
import com.globo.votacaoparedao.entity.Votacao;
import com.globo.votacaoparedao.entity.Voto;
import com.globo.votacaoparedao.exception.BusinessException;
import com.globo.votacaoparedao.exception.NotFoundException;
import com.globo.votacaoparedao.repository.ParedaoRepository;
import com.globo.votacaoparedao.repository.VotacaoRepository;
import com.globo.votacaoparedao.service.VotacaoServiceImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class VotacaoServiceTest {

    @Mock
    public VotacaoRepository votingRepository;

    @Mock
    public ParedaoRepository agendaRepository;

    @Mock
    public ModelMapper modelMapper;

    @InjectMocks
    public VotacaoServiceImpl votingService;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(votingService, "modelMapper", new ModelMapper());
    }

    @Test
    public void shouldReturnZeroVotings() {
        List<VotacaoResponseDto> resp = votingService.listVotacoes();
        assertEquals(0, resp.size());
    }

    @Test
    public void shouldReturnVotings() {
        List<Votacao> votings = new ArrayList<>();
        votings.add(new Votacao());
        votings.add(new Votacao());

        Mockito.when(votingRepository.findAll()).thenReturn(votings);

        List<VotacaoResponseDto> resp = votingService.listVotacoes();
        assertEquals(2, resp.size());
    }

    @Test
    public void shouldReturnOneVoting() {
        ObjectId id = new ObjectId();
        Votacao voting = new Votacao();
        voting.setId(id);

        Mockito.when(votingRepository.findById(id)).thenReturn(java.util.Optional.of(voting));

        VotacaoResponseDto resp = votingService.getVotacao(id.toHexString());
        assertEquals(id.toHexString(), resp.getId());
    }

    @Test
    public void shouldCreateVoting() {
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");

        Votacao voting = new Votacao(agenda, 10);
        voting.setId(id);

        Mockito.when(agendaRepository.findById(id)).thenReturn(java.util.Optional.of(agenda));
        Mockito.when(votingRepository.insert(new Votacao(agenda, 10))).thenReturn(voting);

        VotacaoRequestDto req = new VotacaoRequestDto();
        req.setAgendaId(voting.getId().toHexString());
        req.setMinutesToExpiration(10);
        VotacaoResponseDto resp = votingService.createVoting(req);

        assertEquals(id.toHexString(), resp.getId());
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotCreateVotingWhenAgendaNotExists() {
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");

        Votacao voting = new Votacao(agenda, 10);
        voting.setId(id);

        VotacaoRequestDto req = new VotacaoRequestDto();
        req.setAgendaId(voting.getId().toHexString());

        votingService.createVoting(req);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundException(){
        ObjectId id = new ObjectId();
        Mockito.when(votingRepository.findById(id)).thenThrow(new NotFoundException("Voting not found."));

        votingService.getVotacao(id.toHexString());
    }

    @Test
    public void shouldVote(){
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");

        Votacao voting = new Votacao(agenda, 10);
        Mockito.when(votingRepository.findById(id)).thenReturn(java.util.Optional.of(voting));

        Votacao voting2 = new Votacao(agenda, 10);
        voting2.setId(id);

        Voto vote = new Voto("123", Pergunta.Canditado_B);
        voting2.addVote(vote);

        Mockito.when(votingRepository.save(voting)).thenReturn(voting2);

        VotoRequestDto dto = new VotoRequestDto();
        dto.setCpf("123");
        dto.setVotingId(voting2.getId().toHexString());
        dto.setAnswer(Pergunta.Canditado_B);

        VotoResponseDto resp = votingService.addVoto(dto);

        assertTrue(resp.isSuccess());
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowVotingExpired(){
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");
        Votacao voting = new Votacao(agenda, 1);

        voting.setId(id);
        voting.setExpirationDate(voting.getExpirationDate().minusSeconds(61));

        Mockito.when(votingRepository.findById(id)).thenReturn(java.util.Optional.of(voting));

        VotoRequestDto dto = new VotoRequestDto();
        dto.setCpf("123");
        dto.setVotingId(voting.getId().toHexString());
        dto.setAnswer(Pergunta.Canditado_B);
        votingService.addVoto(dto);
    }

    @Test(expected = BusinessException.class)
    public void shouldNotReturnResultWhenVotingIsOpen(){
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");
        agenda.setId(id);

        Votacao voting = new Votacao(new Paredao("test"), 10);
        voting.addVote(new Voto("1", Pergunta.Canditado_B));

        Mockito.when(votingRepository.findById(id)).thenReturn(java.util.Optional.of(voting));

        votingService.getResultadoVotacao(id.toHexString());
    }

    @Test
    public void shouldReturnVotingResult(){
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test");
        agenda.setId(id);

        Votacao voting = new Votacao(new Paredao("test"), 1);
        voting.addVote(new Voto("1", Pergunta.Canditado_B));
        voting.addVote(new Voto("2", Pergunta.Canditado_B));
        voting.addVote(new Voto("3", Pergunta.Canditado_A));
        voting.setExpirationDate(Instant.now().minusSeconds(10));

        Mockito.when(votingRepository.findById(id)).thenReturn(java.util.Optional.of(voting));

        VotacaoResultadoResponseDto resp = votingService.getResultadoVotacao(id.toHexString());

        assertEquals(2, resp.getVoteCount().getCandidato_B());
        assertEquals(1, resp.getVoteCount().getCandidato_A());
    }
}