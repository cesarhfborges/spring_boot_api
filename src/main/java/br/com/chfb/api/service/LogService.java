package br.com.chfb.api.service;

import br.com.chfb.api.model.Log;
import br.com.chfb.api.model.NivelLog;
import br.com.chfb.api.repository.LogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository repository;

    @Transactional
    public void info(
            String origem,
            String acao,
            String mensagem
    ) {

        Log log = new Log();

        log.setNivel(NivelLog.INFO);
        log.setOrigem(origem);
        log.setAcao(acao);
        log.setMensagem(mensagem);

        repository.save(log);
    }

}
