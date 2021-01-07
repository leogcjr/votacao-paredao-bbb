package com.globo.votacaoparedao.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.globo.votacaoparedao.dto.ParedaoRequestDto;
import com.globo.votacaoparedao.dto.ParedaoResponseDto;
import com.globo.votacaoparedao.service.ParedaoService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api(value = "paredao")
@RequestMapping(value = "api/v1/paredao", produces = "application/json")
public class ParedaoController {

    private final ParedaoService paredaoService;

    @Autowired
    public ParedaoController(ParedaoService paredaoService) {
        this.paredaoService = paredaoService;
    }

    @ApiOperation(value="busca todos paredoes", response = ParedaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Agendas found.")
    })
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.paredaoService.listParedaos());
    }

    @ApiOperation(value="busca um paredao", response = ParedaoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Paredao encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return ResponseEntity.ok(this.paredaoService.getParedao(id));
    }

    @ApiOperation(value="cria um paredao", response = ParedaoResponseDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Paredao Criado com sucessp.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ParedaoRequestDto paredao) throws URISyntaxException {
        ParedaoResponseDto response = this.paredaoService.createParedao(paredao);
        return ResponseEntity.created(new URI(response.getId())).body(response);
    }

}
