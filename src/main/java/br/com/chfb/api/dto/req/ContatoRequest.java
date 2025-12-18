package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;

public record ContatoRequest(
        @Schema(
                description = "Tipo do contato",
                example = "EMAIL",
                allowableValues = {"EMAIL", "TELEFONE"}
        )
        TipoContato tipo,
        String valor
) {
}
