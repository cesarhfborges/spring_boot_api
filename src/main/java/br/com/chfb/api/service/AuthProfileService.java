package br.com.chfb.api.service;

import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.dto.resp.PerfilResponse;
import br.com.chfb.api.dto.resp.UsuarioPerfilResponse;
import br.com.chfb.api.model.Perfil;
import br.com.chfb.api.model.Role;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthProfileService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioPerfilResponse getPerfilUsuarioLogado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioPerfilResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                mapProfile(usuario.getPerfil())
        );
    }

    private PerfilResponse mapProfile(Perfil perfil) {

        if (perfil == null) {
            return null;
        }

        return new PerfilResponse(
                perfil.getNome(),
                perfil.getSobrenome(),
                perfil.getDataNascimento(),
                perfil.getCpf(),
                perfil.getRg(),
                perfil.getEnderecos()
                        .stream()
                        .map(e -> new EnderecoResponse(
                                e.getLogradouro(),
                                e.getNumero(),
                                e.getComplemento(),
                                e.getBairro(),
                                e.getCidade(),
                                e.getUf(),
                                e.getCep()
                        ))
                        .collect(Collectors.toSet()),
                perfil.getContatos()
                        .stream()
                        .map(c -> new ContatoResponse(
                                c.getTipo(),
                                c.getValor()
                        ))
                        .collect(Collectors.toSet())
        );
    }
}
