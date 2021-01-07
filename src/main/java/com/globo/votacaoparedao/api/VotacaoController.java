package com.globo.votacaoparedao.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.globo.votacaoparedao.dto.VotacaoRequestDto;
import com.globo.votacaoparedao.dto.VotacaoResponseDto;
import com.globo.votacaoparedao.dto.VotoRequestDto;
import com.globo.votacaoparedao.dto.VotoResponseDto;
import com.globo.votacaoparedao.service.VotacaoService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api(value = "votacao")
@RequestMapping(value = "api/v1/votacao", produces = "application/json")
public class VotacaoController {

    private final VotacaoService votacaoService;

    @Autowired
    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    @ApiOperation(value="busca todas votacoes", response = VotacaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Votacao encontrada.")
    })
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.votacaoService.listVotacoes());
    }

    @ApiOperation(value="busca uma Votacao", response = VotacaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Votacao encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return ResponseEntity.ok(this.votacaoService.getVotacao(id));
    }

    @ApiOperation(value="cria uma votacao", response = VotacaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Votacao criada com sucesso.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody VotacaoRequestDto voting) throws URISyntaxException {
        VotacaoResponseDto response = this.votacaoService.createVoting(voting);
        return ResponseEntity.created(new URI(response.getId())).body(response);
    }

    @ApiOperation(value="voto computado a votacao", response = VotacaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Voto computado com sucesoo.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/vote")
    public ResponseEntity<?> vote(@RequestBody VotoRequestDto vote) throws URISyntaxException {
        VotoResponseDto response = this.votacaoService.addVoto(vote);
        return ResponseEntity.created(new URI(response.toString())).body(response);
    }

    @ApiOperation(value="get resultado da votacao", response = VotacaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resultado da votacao encontrado.")
    })
    @GetMapping("/result/{id}")
    public ResponseEntity<?> getResultadoVotacao(@PathVariable String id){
        return ResponseEntity.ok(this.votacaoService.getResultadoVotacao(id));
    }
}
