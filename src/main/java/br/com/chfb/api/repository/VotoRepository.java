package br.com.chfb.api.repository;

import br.com.chfb.api.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByPauta_IdAndFuncionario_Id(Long pautaId, Long funcionarioId);

    boolean existsByPautaIdAndFuncionarioId(Long pautaId, Long funcionarioId);
}