package br.com.chfb.api.repository;

import br.com.chfb.api.model.ParticipacaoReuniao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacaoReuniaoRepository
        extends JpaRepository<ParticipacaoReuniao, Long> {

    boolean existsByReuniao_IdAndFuncionario_Id(Long reuniaoId, Long funcionarioId);
}
