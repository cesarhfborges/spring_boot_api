package br.com.chfb.api.service;

import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.model.Voto;
import br.com.chfb.api.repository.PautaRepository;
import br.com.chfb.api.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository repository;
    private final PautaRepository pautaRepository;

    public Voto salvar(
            Long reuniaoId,
            Long pautaId,
            Voto voto
    ) {
        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        voto.setId(null);
        voto.setPauta(pauta);
        voto.setDataHoraVoto(LocalDateTime.now());

        return repository.save(voto);
    }

    public Voto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voto não encontrado"));
    }

    public List<Voto> buscarTodos() {
        return repository.findAll();
    }
}
