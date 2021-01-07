package com.globo.votacaoparedao.service;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.globo.votacaoparedao.dto.*;
import com.globo.votacaoparedao.entity.*;
import com.globo.votacaoparedao.exception.BusinessException;
import com.globo.votacaoparedao.exception.NotFoundException;
import com.globo.votacaoparedao.integration.CpfService;
import com.globo.votacaoparedao.repository.ParedaoRepository;
import com.globo.votacaoparedao.repository.VotacaoRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private final ParedaoRepository paredaoRepository;
    private final ModelMapper modelMapper;
    private final Environment environment;
    private final CpfService cpfService;
    private final MessagingService messagingService;

    @Autowired
    public VotacaoServiceImpl(VotacaoRepository votacaoRepository, ModelMapper modelMapper, ParedaoRepository paredaoRepository,
                             Environment environment, CpfService cpfService, MessagingService messagingService) {
        this.votacaoRepository = votacaoRepository;
        this.paredaoRepository = paredaoRepository;
        this.modelMapper = modelMapper;
        this.environment = environment;
        this.cpfService = cpfService;
        this.messagingService = messagingService;
    }

    @Override
    public List<VotacaoResponseDto> listVotacoes() {
        List<Votacao> votingList = this.votacaoRepository.findAll();

        return votingList.stream().map(
                voting -> modelMapper.map(voting, VotacaoResponseDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public VotacaoResponseDto getVotacao(String id) {
        Votacao voting = findVoting(id);
        return modelMapper.map(voting, VotacaoResponseDto.class);
    }

    @Override
    public VotacaoResponseDto createVoting(VotacaoRequestDto dto) {
        Paredao agenda = this.paredaoRepository.findById(new ObjectId(dto.getAgendaId())).
                orElseThrow(() -> new NotFoundException("Agenda not found."));

        Integer minutesToExpiration = dto.getMinutesToExpiration();
        if (minutesToExpiration == null || minutesToExpiration <= 0)
            minutesToExpiration = (Integer.parseInt(Objects.requireNonNull(environment.getProperty("default.expiration.minutes"))));

        Votacao voting = new Votacao(agenda, minutesToExpiration);
        voting = this.votacaoRepository.insert(voting);

        return modelMapper.map(voting, VotacaoResponseDto.class);
    }

    @Override
    public VotoResponseDto addVoto(VotoRequestDto dto) {
        Votacao voting = findVoting(dto.getVotingId());

        validateVote(voting, dto);

        Voto vote = new Voto(dto.getCpf(), dto.getAnswer());
        voting.addVote(vote);
        votacaoRepository.save(voting);

        return new VotoResponseDto(true);
    }

    @Override
    public VotacaoResultadoResponseDto getResultadoVotacao(String id) {
        Votacao voting = findVoting(id);

        if (!voting.isExpired())
            throw new BusinessException("A votação ainda está aberta, será encerrada às " + voting.getExpirationDate().toString());

        List<Voto> votes = voting.getVotes();

        ContadorVotos voteCount = new ContadorVotos(
                votes.stream().filter(vote -> vote.getAnswer().equals(Pergunta.YES)).count(),
                votes.stream().filter(vote -> vote.getAnswer().equals(Pergunta.NO)).count()
        );

        VotacaoResultadoResponseDto resultResponseDto = new VotacaoResultadoResponseDto();
        resultResponseDto.setAgenda(modelMapper.map(voting.getAgenda(), ParedaoResponseDto.class));
        resultResponseDto.setVoteCount(voteCount);

        return resultResponseDto;
    }

    private Votacao findVoting(String id){
       return this.votacaoRepository.findById(new ObjectId(id)).
                orElseThrow(() -> new NotFoundException("Votação não encontrada."));
    }

    private void validateVote(Votacao voting, VotoRequestDto dto){
        if (voting.isExpired())
            throw new BusinessException("A votação já expirou.");

        if (voting.cpfAlreadyVoted(dto.getCpf()))
            throw new BusinessException("O CPF ("+dto.getCpf()+") j[a votou.");

        if (cpfService.isAbleToVote(dto.getCpf()))
            throw new BusinessException("CPF não pode votar.");
    }

    @Scheduled(fixedDelay = 1000)
    private void closeAndBroadcastVotingResult() {
        List<Votacao> votingList = findAllExpiredVotingsButNotClosed();

        votingList.forEach(voting -> {
            VotacaoResultadoResponseDto votingResult = getResultadoVotacao(voting.getId().toHexString());
            messagingService.send(votingResult);

            voting.setClosed(true);
            votacaoRepository.save(voting);
        });
    }

    private List<Votacao> findAllExpiredVotingsButNotClosed() {
        return votacaoRepository.findAll().stream().filter(
                voting -> voting.isExpired() && !voting.isClosed()
        ).collect(Collectors.toList());
    }
}
