package br.com.chfb.api.service;

import br.com.chfb.api.dto.req.AbrirVotacaoRequest;
import br.com.chfb.api.model.*;
import br.com.chfb.api.repository.OpcaoVotoRepository;
import br.com.chfb.api.repository.PautaRepository;
import br.com.chfb.api.repository.VotoRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository repository;
    private final PautaRepository pautaRepository;
    private final PautaService pautaService;
    private final OpcaoVotoRepository opcaoVotoRepository;
    private final BloqueioVotoPautaService bloqueioVotoPautaService;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

    @Transactional
    public Voto salvar(
            Long reuniaoId,
            Long pautaId,
            Voto voto,
            List<Long> opcoesSelecionadas,
            String codigoVoto
    ) {
        /* =========================
         * Buscar pauta
         * ========================= */
        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        Funcionario funcionario = usuarioLogadoProvider
                .getUsuarioLogado()
                .getFuncionario();

        /* =========================
         * 1. Bloqueio de voto
         * ========================= */
        bloqueioVotoPautaService.validarFuncionarioPodeVotar(
                pautaId,
                funcionario.getId()
        );

        /* =========================
         * 2. Pauta aberta
         * ========================= */
        pautaService.validarPautaAbertaParaVotacao(pauta);

        /* =========================
         * 3. Voto duplicado
         * ========================= */
        if (repository.existsByPautaIdAndFuncionarioId(pautaId, funcionario.getId())) {
            throw new IllegalStateException("Funcionário já votou nesta pauta");
        }

        /* =========================
         * 4. Limite de seleções
         * ========================= */
        if (pauta.getLimiteSelecoes() != null) {

            if (opcoesSelecionadas == null || opcoesSelecionadas.isEmpty()) {
                throw new IllegalStateException("Nenhuma opção de voto foi selecionada");
            }

            if (opcoesSelecionadas.size() > pauta.getLimiteSelecoes()) {
                throw new IllegalStateException(
                        "Número de opções selecionadas excede o limite permitido"
                );
            }
        }

        /* =========================
         * 5. Código de voto
         * ========================= */
        if (pauta.getExigeCodigoVoto()) {

            if (codigoVoto == null || codigoVoto.isBlank()) {
                throw new IllegalStateException("Código de voto é obrigatório para esta pauta");
            }

            if (!codigoVoto.equals(pauta.getCodigoVoto())) {
                throw new IllegalStateException("Código de voto inválido");
            }
        }

        /* =========================
         * 6. Criar voto
         * ========================= */
        voto.setId(null);
        voto.setPauta(pauta);
        voto.setFuncionario(funcionario);
        voto.setDataHoraVoto(LocalDateTime.now());

        List<ItemVotado> itens = opcoesSelecionadas.stream()
                .map(opcaoId -> {

                    OpcaoVoto opcao = opcaoVotoRepository.findById(opcaoId)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Opção de voto não encontrada")
                            );

                    // 🔒 Garantir que a opção pertence à pauta
                    if (!opcao.getPauta().getId().equals(pautaId)) {
                        throw new IllegalStateException(
                                "Opção de voto não pertence à pauta informada"
                        );
                    }

                    ItemVotado item = new ItemVotado();
                    item.setVoto(voto);
                    item.setOpcaoVoto(opcao);

                    return item;
                })
                .toList();

        voto.setItensVotados(itens);

        return repository.save(voto);
    }

    @Transactional
    public Pauta abrirVotacao(
            Long reuniaoId,
            Long pautaId,
            AbrirVotacaoRequest request
    ) {

        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        /* =========================
         * Validação de status
         * ========================= */
        if (pauta.getStatus() == StatusPauta.CANCELADA) {
            throw new IllegalStateException("A votação se encontra cancelada.");
        }

        if (pauta.getStatus() == StatusPauta.ENCERRADA) {
            throw new IllegalStateException("A votação já foi encerrada.");
        }

        if (pauta.getStatus() == StatusPauta.ABERTA) {
            throw new IllegalStateException("A votação já foi aberta.");
        }

        /* =========================
         * Código de voto
         * ========================= */
        boolean exigeCodigo = Boolean.TRUE.equals(request.exigeCodigoVoto());
        pauta.setExigeCodigoVoto(exigeCodigo);

        if (exigeCodigo) {

            String codigo = request.codigoVoto();

            if (codigo == null || codigo.isBlank()) {
                codigo = gerarCodigoNumerico6Digitos();
            }

            pauta.setCodigoVoto(codigo);

        } else {
            pauta.setCodigoVoto(null);
        }

        pauta.setTempo(request.tempo());

        /* =========================
         * Abrir votação
         * ========================= */
        pauta.setStatus(StatusPauta.ABERTA);
        pauta.setDataHoraAbertura(LocalDateTime.now());

        return pautaRepository.save(pauta);
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

    @Transactional
    public void cancelarVotacao(Long reuniaoId, Long pautaId) {

        Pauta pauta = pautaRepository
                .findByIdAndReuniaoId(pautaId, reuniaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada para a reunião informada")
                );

        pauta.setStatus(StatusPauta.CANCELADA);

        pautaRepository.save(pauta);
    }

    private String gerarCodigoNumerico6Digitos() {
        int numero = ThreadLocalRandom.current().nextInt(0, 1_000_000);
        return String.format("%06d", numero);
    }
}
