package br.com.chfb.api.dto.req;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;

public record AbrirVotacaoRequest(
        @NotNull(message = "Informe se a votação exigirá código")
        Boolean exigeCodigoVoto,

        String codigoVoto
) {
    public AbrirVotacaoRequest {
        if (Boolean.TRUE.equals(exigeCodigoVoto)) {
            if (codigoVoto != null && !codigoVoto.isBlank()) {
                if (!codigoVoto.matches("\\d{6}")) {
                    throw new ValidationException(
                            "O código de votação deve conter exatamente 6 números ou ser deixado em branco"
                    );
                }
            }
        }
    }
}
