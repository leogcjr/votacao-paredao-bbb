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


import com.globo.votacaoparedao.dto.ParedaoRequestDto;
import com.globo.votacaoparedao.dto.ParedaoResponseDto;
import com.globo.votacaoparedao.service.ParedaoService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ParedaoControllerTest {

    @Mock
    public ParedaoService paredaoService;

    @InjectMocks
    public ParedaoController paredaoController;

    @Test
    public void shouldReturnZeroAgendas() {
        ResponseEntity<?> resp = paredaoController.getAll();

        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(0, ((LinkedList<?>) resp.getBody()).size());
    }

    @Test
    public void shouldReturnAgendas() {
        List<ParedaoResponseDto> listDto = new ArrayList<>();
        listDto.add(new ParedaoResponseDto("1","test"));

        Mockito.when(paredaoService.listParedaos()).thenReturn(listDto);

        ResponseEntity<?> resp = paredaoController.getAll();

        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) resp.getBody()).size());
    }

    @Test
    public void shouldCreateAgenda() throws URISyntaxException {
        String agendaName = "test";
        ParedaoRequestDto req = new ParedaoRequestDto(agendaName);
        ParedaoResponseDto resp = new ParedaoResponseDto(new ObjectId().toHexString(), agendaName);
        Mockito.when(paredaoService.createParedao(req)).thenReturn(resp);

        ResponseEntity<?> response = paredaoController.create(req);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
    }
}