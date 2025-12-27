package br.com.chfb.api.repository;

import br.com.chfb.api.model.BloqueioVotoPauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloqueioVotoPautaRepository
        extends JpaRepository<BloqueioVotoPauta, Long> {

    Optional<BloqueioVotoPauta> findByFuncionario_IdAndPauta_IdAndAtivoTrue(
            Long funcionarioId, Long pautaId
    );

    List<BloqueioVotoPauta> findAllByPautaId(Long pautaId);

    Optional<BloqueioVotoPauta> findByIdAndPautaId(Long id, Long pautaId);
}
