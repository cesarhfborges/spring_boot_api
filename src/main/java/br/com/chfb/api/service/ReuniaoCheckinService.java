package br.com.chfb.api.service;

import br.com.chfb.api.model.Reuniao;
import br.com.chfb.api.model.ReuniaoCheckin;
import br.com.chfb.api.model.TipoAcesso;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.ReuniaoCheckinRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReuniaoCheckinService {

    private final ReuniaoCheckinRepository repository;

    public boolean possuiCheckin(
            Long reuniaoId,
            Long usuarioId
    ) {

        return repository.existsByReuniaoIdAndUsuarioId(
                reuniaoId,
                usuarioId
        );
    }

    @Transactional
    public void realizarCheckin(
            Reuniao reuniao,
            Usuario usuario,
            String ip,
            String userAgent,
            String tipoAcesso
    ) {

        repository.findByReuniaoIdAndUsuarioId(
                        reuniao.getId(),
                        usuario.getId()
                )
                .ifPresentOrElse(
                        checkin -> {
                            checkin.setOnline(true);
                            checkin.setUltimaAtividade(
                                    LocalDateTime.now()
                            );
                            checkin.setIp(ip);
                            checkin.setUserAgent(userAgent);

                            repository.save(checkin);
                        },
                        () -> {
                            repository.save(
                                    ReuniaoCheckin.builder()
                                            .reuniao(reuniao)
                                            .usuario(usuario)
                                            .dataHoraEntrada(
                                                    LocalDateTime.now()
                                            )
                                            .ultimaAtividade(
                                                    LocalDateTime.now()
                                            )
                                            .online(true)
                                            .ip(ip)
                                            .userAgent(userAgent)
                                            .tipoAcesso(
                                                    TipoAcesso.from(tipoAcesso.toUpperCase())
                                            )
                                            .build()
                            );
                        }
                );
    }
}
