package br.com.chfb.api.dto.resp;

import java.util.Date;

public record CheckinResponse(
        boolean realizado,
        Date dataHora
) {
}
