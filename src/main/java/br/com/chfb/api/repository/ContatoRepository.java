package br.com.chfb.api.repository;

import br.com.chfb.api.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    Contato findContatoByIdAndFuncionario_Id(Long id, Long funcionarioId);
}
