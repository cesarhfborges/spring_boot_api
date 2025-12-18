package br.com.chfb.api.dto.resp;

import br.com.chfb.api.model.UF;

public record EnderecoResponse(
        Long id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        UF uf,
        String cep
) {
}