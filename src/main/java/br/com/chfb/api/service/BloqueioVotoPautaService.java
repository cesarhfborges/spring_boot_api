package br.com.chfb.api.service;

import br.com.chfb.api.model.BloqueioVotoPauta;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.repository.BloqueioVotoPautaRepository;
import br.com.chfb.api.repository.FuncionarioRepository;
import br.com.chfb.api.repository.PautaRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloqueioVotoPautaService {

    private final FuncionarioRepository funcionarioRepository;
    private final BloqueioVotoPautaRepository repository;
    private final PautaRepository pautaRepository;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

    @Transactional
    public BloqueioVotoPauta salvar(
            Long reuniaoId,
            Long pautaId,
            BloqueioVotoPauta bloqueio,
            Long funcionarioId
    ) {
        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Funcionário não encontrado")
                );

        bloqueio.setId(null);
        bloqueio.setPauta(pauta);
        bloqueio.setFuncionario(funcionario);
        bloqueio.setMotivo(bloqueio.getMotivo());
        bloqueio.setDataInclusao(LocalDateTime.now());
        bloqueio.setIncluidoPor(usuarioLogadoProvider.getUsuarioLogado());
        bloqueio.setAtivo(true);

        return repository.save(bloqueio);
    }

    @Transactional(readOnly = true)
    public List<BloqueioVotoPauta> listarPorPauta(
            Long reuniaoId,
            Long pautaId
    ) {
        validarPauta(reuniaoId, pautaId);
        return repository.findAllByPautaId(pautaId);
    }

    @Transactional(readOnly = true)
    public BloqueioVotoPauta buscarPorId(
            Long reuniaoId,
            Long pautaId,
            Long id
    ) {
        validarPauta(reuniaoId, pautaId);

        return repository.findByIdAndPautaId(id, pautaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Bloqueio não encontrado para a pauta informada")
                );
    }

    @Transactional
    public BloqueioVotoPauta atualizar(
            Long reuniaoId,
            Long pautaId,
            Long id,
            BloqueioVotoPauta atualizado
    ) {
        BloqueioVotoPauta bloqueio = this.buscarPorId(reuniaoId, pautaId, id);

        bloqueio.setMotivo(atualizado.getMotivo());
        bloqueio.setAtivo(atualizado.isAtivo());

        return repository.save(bloqueio);
    }

    @Transactional
    public void excluir(
            Long reuniaoId,
            Long pautaId,
            Long id
    ) {
        BloqueioVotoPauta bloqueio = this.buscarPorId(reuniaoId, pautaId, id);
        repository.delete(bloqueio);
    }

    @Transactional
    public List<BloqueioVotoPauta> buscarTodos() {
        return repository.findAll();
    }

    private void validarPauta(Long reuniaoId, Long pautaId) {
        if (!pautaRepository.existsByIdAndReuniaoId(pautaId, reuniaoId)) {
            throw new EntityNotFoundException("Pauta não encontrada para a reunião informada");
        }
    }
}
