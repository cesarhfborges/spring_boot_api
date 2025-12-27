package br.com.chfb.api.repository;

import br.com.chfb.api.model.Pauta;
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
}
