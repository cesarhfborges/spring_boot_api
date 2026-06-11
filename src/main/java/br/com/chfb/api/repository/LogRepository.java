package br.com.chfb.api.repository;

import br.com.chfb.api.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
