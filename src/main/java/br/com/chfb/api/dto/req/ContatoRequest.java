package br.com.chfb.api.dto.req;

import br.com.chfb.api.model.TipoContato;

public record ContatoRequest(
        TipoContato tipo,
        String valor
) {
}
