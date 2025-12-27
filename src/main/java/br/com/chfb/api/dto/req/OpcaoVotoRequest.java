package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotBlank;

public record OpcaoVotoRequest(

        @NotBlank(message = "Titulo é obrigatória")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao

//        @PositiveOrZero(message = "Ordem deve ser zero ou positiva")
//        Integer ordem
) {
}
