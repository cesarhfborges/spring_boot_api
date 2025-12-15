package br.com.chfb.api.dto.resp;

import java.util.Set;

public record UsuarioPerfilResponse(
        Long id,
        String username,
        Set<String> roles,
        PerfilResponse perfil
) {
}
