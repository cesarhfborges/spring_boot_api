package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.PerfilMapper;
import br.com.chfb.api.dto.resp.UsuarioPerfilResponse;
import br.com.chfb.api.model.Role;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthProfileService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper perfilMapper;

    public UsuarioPerfilResponse getPerfilUsuarioLogado() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByUsernameWithProfile(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioPerfilResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                perfilMapper.toDTO(usuario.getPerfil())
        );
    }
}
