package br.com.chfb.api.service;

import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.model.StatusPauta;
import br.com.chfb.api.model.Voto;
import br.com.chfb.api.repository.PautaRepository;
import br.com.chfb.api.repository.VotoRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository repository;
    private final PautaRepository pautaRepository;
    private final BloqueioVotoPautaService bloqueioVotoPautaService;
    private final PautaService pautaService;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

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

        Long funcionarioId = usuarioLogadoProvider.getUsuarioLogado().getFuncionario().getId();

        bloqueioVotoPautaService.validarFuncionarioPodeVotar(
                pautaId,
                funcionarioId
        );

        pautaService.validarPautaAbertaParaVotacao(pauta);

        if (repository.existsByPautaIdAndFuncionarioId(pautaId, funcionarioId)) {
            throw new IllegalStateException("Funcionário já votou nesta pauta");
        }

        voto.setId(null);
        voto.setPauta(pauta);
        voto.setFuncionario(usuarioLogadoProvider.getUsuarioLogado().getFuncionario());
        voto.setDataHoraVoto(LocalDateTime.now());

        return repository.save(voto);
    }

    @Transactional
    public void abrirVotacao(Long reuniaoId, Long pautaId) {

        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        if (pauta.getStatus() == StatusPauta.CANCELADA) {
            throw new IllegalStateException("A votação se encontra cancelada.");
        }

        if (pauta.getStatus() == StatusPauta.ENCERRADA) {
            throw new IllegalStateException("A votação já foi encerrada.");
        }

        if (pauta.getStatus() == StatusPauta.ABERTA) {
            throw new IllegalStateException("A votação já foi aberta.");
        }

        pauta.setStatus(StatusPauta.ABERTA);
        pauta.setDataHoraAbertura(LocalDateTime.now());

        pautaRepository.save(pauta);
    }

    @Transactional
    public void encerrarVotacao(Long reuniaoId, Long pautaId) {

        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        if (pauta.getStatus() != StatusPauta.ABERTA) {
            throw new IllegalStateException("A votação não está aberta");
        }

        pauta.setStatus(StatusPauta.ENCERRADA);
        pauta.setDataHoraEncerramento(LocalDateTime.now());

        pautaRepository.save(pauta);
    }
}
