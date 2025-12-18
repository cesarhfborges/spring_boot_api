package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ContatoRequest(
        @Schema(
                description = "Tipo do contato",
                example = "EMAIL",
                allowableValues = {"EMAIL", "TELEFONE"}
        )
        @NotBlank(message = "Tipo é obrigatório")
        TipoContato tipo,

        @NotBlank(message = "valor é obrigatório")
        String valor
) {
}
