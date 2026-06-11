package br.com.chfb.api.repository;

import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.model.StatusPauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Optional<Pauta> findByIdAndReuniaoId(Long id, Long reuniaoId);

    List<Pauta> findAllByReuniaoId(Long reuniaoId);

    List<Pauta> findAllByReuniaoIdOrderByOrdemAsc(Long reuniaoId);

    @Query("""
                select coalesce(max(p.ordem), 0) + 1
                from Pauta p
                where p.reuniao.id = :reuniaoId
            """)
    Integer proximaOrdem(@Param("reuniaoId") Long reuniaoId);

    boolean existsByIdAndReuniaoId(Long id, Long reuniaoId);

    List<Pauta> findByStatus(StatusPauta status);

    @Query("""
            select p
            from Pauta p
            where p.status = :status
              and p.dataHoraAbertura is not null
              and p.tempo is not null
            """)
    List<Pauta> findPautasAbertasComTempo(
            @Param("status") StatusPauta status
    );
}
