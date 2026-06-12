package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoVotacao;
import br.com.chfb.api.model.TipoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PautaRequest(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Tipo de voto é obrigatório")
        TipoVoto tipoVoto,

        @NotNull(message = "Tipo da votação é obrigatório")
        TipoVotacao tipoVotacao,

        @PositiveOrZero(message = "Limite deve ser zero ou positivo")
        Integer limiteSelecoes
) {
}
