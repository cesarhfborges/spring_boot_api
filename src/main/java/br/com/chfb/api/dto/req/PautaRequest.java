package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PautaRequest(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        String descricao,

        @NotNull(message = "Tipo de voto é obrigatório")
        TipoVoto tipoVoto,

        @PositiveOrZero(message = "Limite deve ser zero ou positivo")
        Integer limiteSelecoes,

        @NotNull(message = "Deve ser informado se é exigido codigo ou não.")
        boolean exigeCodigoVoto
) {
}
