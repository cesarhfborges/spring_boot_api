package br.com.chfb.api.security;

import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioLogadoProvider {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
