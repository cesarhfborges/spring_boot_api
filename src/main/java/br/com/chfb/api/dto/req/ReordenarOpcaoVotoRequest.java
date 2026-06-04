package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReordenarOpcaoVotoRequest (
        @NotNull
        Long id,

        @NotNull
        @PositiveOrZero
        Integer ordem
){
}
