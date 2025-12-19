package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.UF;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequest(
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "Numero é obrigatório")
        String numero,

        @NotBlank(message = "Complemento é obrigatório")
        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @NotBlank(message = "Cidade é obrigatório")
        String cidade,

        @NotBlank(message = "UF é obrigatório")
        String uf,

        @NotBlank(message = "Cep é obrigatório")
        @Pattern(
                regexp = "\\d{8}",
                message = "CEP deve conter exatamente 8 dígitos numéricos"
        )
        String cep
) {
        @AssertTrue(message = "UF inválida")
        public boolean isUfValida() {
                return UF.isValid(uf);
        }
}
