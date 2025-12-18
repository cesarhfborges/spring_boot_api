package br.com.chfb.api.dto.req;

import jakarta.validation.constraints.NotBlank;

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
        String cep
) {
}
