package br.com.chfb.api.dto.resp;

import br.com.chfb.api.model.StatusPauta;
import br.com.chfb.api.model.TipoVoto;

import java.time.LocalDateTime;

public record PautaResponse(
        Long id,
        String titulo,
        String descricao,
        TipoVoto tipoVoto,
        Integer limiteSelecoes,
        boolean exigeCodigoVoto,
        LocalDateTime dataHoraAbertura,
        LocalDateTime dataHoraEncerramento,
        StatusPauta status,
        Long ordem
) {
}
