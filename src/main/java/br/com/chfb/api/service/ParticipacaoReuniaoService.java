package br.com.chfb.api.service;

import br.com.chfb.api.model.ParticipacaoReuniao;
import br.com.chfb.api.repository.ParticipacaoReuniaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipacaoReuniaoService {

    private final ParticipacaoReuniaoRepository repository;

    public ParticipacaoReuniao salvar(ParticipacaoReuniao participacao) {
        return repository.save(participacao);
    }
}
