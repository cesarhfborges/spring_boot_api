package br.com.chfb.api.dto.resp;

import java.time.LocalDateTime;

public record BloqueioVotoPautaResponse(
        Long id,
        Long funcionarioId,
        Long pautaId,
        String motivo,
        LocalDateTime dataInclusao,
        boolean ativo
) {
}
