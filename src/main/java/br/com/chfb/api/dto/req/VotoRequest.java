package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record VotoRequest(

//        @NotNull(message = "ID da pauta é obrigatório")
//        Long pautaId,

        @NotEmpty(message = "Ao menos uma opção deve ser selecionada")
        List<Long> opcoesSelecionadasIds,

        String codigoVoto
) {
}
