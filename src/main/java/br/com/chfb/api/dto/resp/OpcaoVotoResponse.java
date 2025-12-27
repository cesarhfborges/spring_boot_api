package br.com.chfb.api.dto.resp;

public record OpcaoVotoResponse(
        Long id,
        String titulo,
        String descricao,
        Integer ordem
) {
}
