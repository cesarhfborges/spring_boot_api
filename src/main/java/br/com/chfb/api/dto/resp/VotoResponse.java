package br.com.chfb.api.dto.resp;

import java.time.LocalDateTime;
import java.util.List;

public record VotoResponse(
        Long id,
        Long pautaId,
        Long funcionarioId,
        LocalDateTime dataHoraVoto,
        List<Long> opcoesSelecionadas
) {
}
