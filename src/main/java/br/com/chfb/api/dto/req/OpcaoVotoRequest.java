package br.com.chfb.api.dto.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record OpcaoVotoRequest(

        @NotBlank(message = "Titulo é obrigatória")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @Nullable
        String icone,

        @Nullable
        @PositiveOrZero(message = "Ordem deve ser zero ou positiva")
        Integer ordem
) {
}
