package br.com.chfb.api.service;

import br.com.chfb.api.dto.resp.EventoReuniao;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publicar(EventoReuniao evento) {

        messagingTemplate.convertAndSend(
                "/topic/reunioes/" + evento.reuniaoId(),
                evento
        );
    }
}
