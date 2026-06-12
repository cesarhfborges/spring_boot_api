package br.com.chfb.api.repository;

import br.com.chfb.api.model.ReuniaoCheckin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReuniaoCheckinRepository extends JpaRepository<ReuniaoCheckin, Long> {

    Optional<ReuniaoCheckin> findByReuniaoIdAndUsuarioId(
            Long reuniaoId,
            Long usuarioId
    );

    boolean existsByReuniaoIdAndUsuarioId(
            Long reuniaoId,
            Long usuarioId
    );
}
