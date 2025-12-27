package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotNull;

public record AbrirVotacaoRequest(
        @NotNull(message = "Informe se a votação exigirá código")
        Boolean exigeCodigoVoto,

//        @NotNull(message = "Data/hora de início é obrigatória")
//        LocalDateTime dataHoraInicio,

        String codigoVoto
) {
}
