package br.com.chfb.api.dto.req;

public record EnderecoRequest(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Long funcionarioId
) {
}
