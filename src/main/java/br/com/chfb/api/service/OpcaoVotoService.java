package br.com.chfb.api.service;

import br.com.chfb.api.model.OpcaoVoto;
import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.repository.OpcaoVotoRepository;
import br.com.chfb.api.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpcaoVotoService {

    private final OpcaoVotoRepository repository;
    private final PautaRepository pautaRepository;

    /* ========================= CREATE ========================= */

    public OpcaoVoto salvar(Long reuniaoId, Long pautaId, OpcaoVoto opcao) {

        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        opcao.setId(null);
        opcao.setPauta(pauta);
        Integer proximaOrdem = repository.proximaOrdem(pautaId);
        opcao.setOrdem(proximaOrdem);

        return repository.save(opcao);
    }

    /* ========================= READ ========================= */

    @Transactional(readOnly = true)
    public List<OpcaoVoto> listarPorPauta(Long reuniaoId, Long pautaId) {

        validarPauta(reuniaoId, pautaId);

        return repository.findAllByPautaIdOrderByOrdemAsc(pautaId);
    }

    @Transactional(readOnly = true)
    public OpcaoVoto buscarPorId(Long reuniaoId, Long pautaId, Long id) {

        validarPauta(reuniaoId, pautaId);

        return repository.findByIdAndPautaId(id, pautaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Opção de voto não encontrada para a pauta informada")
                );
    }

    /* ========================= UPDATE ========================= */

    @Transactional
    public OpcaoVoto atualizar(
            Long reuniaoId,
            Long pautaId,
            Long id,
            OpcaoVoto atualizada
    ) {
        OpcaoVoto opcao = buscarPorId(reuniaoId, pautaId, id);

        opcao.setDescricao(atualizada.getDescricao());
        opcao.setOrdem(atualizada.getOrdem());

        return repository.save(opcao);
    }

    /* ========================= DELETE ========================= */

    @Transactional
    public void excluir(Long reuniaoId, Long pautaId, Long id) {
        OpcaoVoto opcao = buscarPorId(reuniaoId, pautaId, id);
        repository.delete(opcao);
    }

    /* ========================= AUX ========================= */

    private void validarPauta(Long reuniaoId, Long pautaId) {
        if (!pautaRepository.existsByIdAndReuniaoId(pautaId, reuniaoId)) {
            throw new EntityNotFoundException("Pauta não encontrada para a reunião informada");
        }
    }
}
