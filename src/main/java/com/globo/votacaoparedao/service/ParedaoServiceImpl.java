package com.globo.votacaoparedao.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globo.votacaoparedao.dto.ParedaoRequestDto;
import com.globo.votacaoparedao.dto.ParedaoResponseDto;
import com.globo.votacaoparedao.entity.Paredao;
import com.globo.votacaoparedao.exception.NotFoundException;
import com.globo.votacaoparedao.repository.ParedaoRepository;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParedaoServiceImpl implements ParedaoService {

    private final ParedaoRepository paredaoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ParedaoServiceImpl(ParedaoRepository paredaoRepository, ModelMapper modelMapper) {
        this.paredaoRepository = paredaoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ParedaoResponseDto> listParedaos() {
        List<Paredao> paredaoList = this.paredaoRepository.findAll();

        return paredaoList.stream().map(
            paredao -> modelMapper.map(paredao, ParedaoResponseDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public ParedaoResponseDto getParedao(String id) {
        Paredao paredao = this.paredaoRepository.findById(new ObjectId(id)).
                orElseThrow(() -> new NotFoundException("paredao not found."));

        return modelMapper.map(paredao, ParedaoResponseDto.class);
    }

    @Override
    public ParedaoResponseDto createParedao(ParedaoRequestDto dto) {
        Paredao paredao = new Paredao(dto.getName());
        paredao = paredaoRepository.insert(paredao);

        return modelMapper.map(paredao, ParedaoResponseDto.class);
    }
}
