package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BloqueioVotoPautaRequest(

        @NotNull(message = "ID do funcionário é obrigatório")
        Long funcionarioId,

        @NotBlank(message = "Motivo do bloqueio é obrigatório")
        String motivo
) {
}
