package br.com.chfb.api.dto.resp;

import br.com.chfb.api.model.TipoContato;

public record ContatoResponse(
        Long id,
        TipoContato tipo,
        String valor
) {
}
