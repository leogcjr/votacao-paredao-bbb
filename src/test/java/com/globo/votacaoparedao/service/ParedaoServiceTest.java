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

import com.globo.votacaoparedao.dto.ParedaoRequestDto;
import com.globo.votacaoparedao.dto.ParedaoResponseDto;
import com.globo.votacaoparedao.entity.Paredao;
import com.globo.votacaoparedao.exception.NotFoundException;
import com.globo.votacaoparedao.repository.ParedaoRepository;
import com.globo.votacaoparedao.service.ParedaoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class ParedaoServiceTest {

    @Mock
    public ParedaoRepository paredaoRepository;

    @Mock
    public ModelMapper modelMapper;

    @InjectMocks
    public ParedaoServiceImpl paredaoService;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(paredaoService, "modelMapper", new ModelMapper());
    }

    @Test
    public void shouldReturnZeroAgendas() {
        List<ParedaoResponseDto> resp = paredaoService.listParedaos();
        assertEquals(0, resp.size());
    }

    @Test
    public void shouldReturnAgendas() {
        List<Paredao> agendas = new ArrayList<>();
        agendas.add(new Paredao("test1"));
        agendas.add(new Paredao("test2"));

        Mockito.when(paredaoRepository.findAll()).thenReturn(agendas);

        List<ParedaoResponseDto> resp = paredaoService.listParedaos();
        assertEquals(2, resp.size());
    }

    @Test
    public void shouldReturnOneAgenda() {
        ObjectId id = new ObjectId();
        Paredao agenda = new Paredao("test1");
        agenda.setId(id);

        Mockito.when(paredaoRepository.findById(id)).thenReturn(java.util.Optional.of(agenda));

        ParedaoResponseDto resp = paredaoService.getParedao(id.toHexString());
        assertEquals(id.toHexString(), resp.getId());
        assertEquals(agenda.getName(), resp.getName());
    }

    @Test
    public void shouldCreateAgenda() {
        ObjectId id = new ObjectId();
        String name = "test1";

        Paredao agenda = new Paredao(name);
        agenda.setId(id);

        Mockito.when(paredaoRepository.insert(new Paredao(name))).thenReturn(agenda);

        ParedaoRequestDto req = new ParedaoRequestDto();
        req.setName(name);
        ParedaoResponseDto resp = paredaoService.createParedao(req);

        assertEquals(id.toHexString(), resp.getId());
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundException(){
        ObjectId id = new ObjectId();
        Mockito.when(paredaoRepository.findById(id)).thenThrow(new NotFoundException("Agenda not found."));

        paredaoService.getParedao(id.toHexString());
    }
}