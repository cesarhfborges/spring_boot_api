package br.com.chfb.api.service;

import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.model.Reuniao;
import br.com.chfb.api.model.StatusPauta;
import br.com.chfb.api.repository.PautaRepository;
import br.com.chfb.api.repository.ReuniaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final ReuniaoRepository reuniaoRepository;

    @Transactional
    public Pauta salvar(Long reuniaoId, Pauta pauta) {

        Reuniao reuniao = reuniaoRepository.findById(reuniaoId)
                .orElseThrow(() -> new EntityNotFoundException("Reunião não encontrada"));

        pauta.setId(null);
        pauta.setReuniao(reuniao);

        if (pauta.getOrdem() == null) {
            Integer proximaOrdem = pautaRepository.proximaOrdem(reuniaoId);
            pauta.setOrdem(proximaOrdem);
        }

        if (pauta.getStatus() == null) {
            pauta.setStatus(StatusPauta.AGUARDANDO);
        }

        return pautaRepository.save(pauta);
    }

    @Transactional(readOnly = true)
    public Pauta buscarPorId(Long reuniaoId, Long pautaId) {
        return pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );
    }

    @Transactional(readOnly = true)
    public List<Pauta> listarPorReuniao(Long reuniaoId) {

        if (!reuniaoRepository.existsById(reuniaoId)) {
            throw new EntityNotFoundException("Reunião não encontrada");
        }

        return pautaRepository.findAllByReuniaoIdOrderByOrdemAsc(reuniaoId);
    }

    @Transactional
    public Pauta atualizar(Long reuniaoId, Long pautaId, Pauta value) {

        Pauta pauta = buscarPorId(reuniaoId, pautaId);

        pauta.setTitulo(value.getTitulo());
        pauta.setDescricao(value.getDescricao());
        pauta.setTipoVoto(value.getTipoVoto());
        pauta.setLimiteSelecoes(value.getLimiteSelecoes());
        pauta.setExigeCodigoVoto(value.isExigeCodigoVoto());
        //pauta.setOrdem(value.getOrdem());

        return pautaRepository.save(pauta);
    }

    @Transactional
    public void excluir(Long reuniaoId, Long pautaId) {

        Pauta pauta = buscarPorId(reuniaoId, pautaId);
        pautaRepository.delete(pauta);
    }

    public void validarPautaAbertaParaVotacao(Pauta pauta) {

        if (pauta.getStatus() != StatusPauta.ABERTA) {
            throw new IllegalStateException("A pauta não está aberta para votação");
        }

        LocalDateTime agora = LocalDateTime.now();

        if (pauta.getDataHoraAbertura() != null && agora.isBefore(pauta.getDataHoraAbertura())) {
            throw new IllegalStateException("A votação ainda não foi aberta");
        }

        if (pauta.getDataHoraEncerramento() != null && agora.isAfter(pauta.getDataHoraEncerramento())) {
            throw new IllegalStateException("A votação já foi encerrada");
        }
    }

//    public List<Pauta> buscarTodos() {
//        return pautaRepository.findAll();
//    }
//
//    public void excluir(Long id) {
//        pautaRepository.delete(buscarPorId(id));
//    }
}
