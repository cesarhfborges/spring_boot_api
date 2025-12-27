package br.com.chfb.api.repository;

import br.com.chfb.api.model.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpcaoVotoRepository extends JpaRepository<OpcaoVoto, Long> {
    List<OpcaoVoto> findAllByPautaIdOrderByOrdemAsc(Long pautaId);

    Optional<OpcaoVoto> findByIdAndPautaId(Long id, Long pautaId);

    @Query("""
                select coalesce(max(o.ordem), 0) + 1
                from OpcaoVoto o
                where o.pauta.id = :pautaId
            """)
    Integer proximaOrdem(@Param("pautaId") Long pautaId);
}
