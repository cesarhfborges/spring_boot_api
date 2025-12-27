package br.com.chfb.api.dto.resp;

import br.com.chfb.api.model.StatusReuniao;

import java.time.LocalDateTime;

public record ReuniaoResponse(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        StatusReuniao status
) {
}
