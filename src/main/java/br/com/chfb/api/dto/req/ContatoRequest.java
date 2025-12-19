package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
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
    @AssertTrue(message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    public boolean isTelefoneValido() {

        if (tipo != TipoContato.TELEFONE) {
            return true; // não valida se não for telefone
        }

        if (valor == null) {
            return false;
        }

        return valor.matches("\\d{10}|\\d{11}");
    }

    @AssertTrue(message = "Email inválido")
    public boolean isEmailValido() {

        if (tipo != TipoContato.EMAIL) {
            return true; // não valida se não for email
        }

        if (valor == null) {
            return false;
        }

        return valor.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
