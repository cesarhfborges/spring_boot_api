package br.com.chfb.api.dto.resp;

public record UsuarioResponse(
        Long id,
        String username,
//        String password,
        Boolean enabled,
        Boolean accountConfirmed
) {
}
