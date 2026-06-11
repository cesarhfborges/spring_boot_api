package br.com.chfb.api.scheduler;

import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.model.StatusPauta;
import br.com.chfb.api.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PautaScheduler {

    private final PautaRepository pautaRepository;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void verificarPautas() {

//        log.debug("Verificando pautas abertas...");

        List<Pauta> pautasAbertas = pautaRepository.findByStatus(StatusPauta.ABERTA);

        LocalDateTime agora = LocalDateTime.now();

        for (Pauta pauta : pautasAbertas) {

            if (pauta.getDataHoraAbertura() == null) {
                continue;
            }

            if (pauta.getTempo() == null || pauta.getTempo().isBlank()) {
                continue;
            }

            Duration duracao = parseDuration(pauta.getTempo());

            LocalDateTime limite = pauta.getDataHoraAbertura().plus(duracao);

            if (agora.isAfter(limite)) {

                pauta.setStatus(StatusPauta.ENCERRADA);
                pauta.setDataHoraEncerramento(agora);

//                log.info("Pauta \"{}\" encerrada automaticamente", pauta.getTitulo());
            }
        }
    }

    private Duration parseDuration(String tempo) {

        String[] partes = tempo.split(":");

        long horas = Long.parseLong(partes[0]);
        long minutos = Long.parseLong(partes[1]);
        long segundos = Long.parseLong(partes[2]);

        return Duration.ofHours(horas).plusMinutes(minutos).plusSeconds(segundos);
    }
}
