package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.PerfilMapper;
import br.com.chfb.api.dto.req.PerfilRequest;
import br.com.chfb.api.dto.resp.PerfilResponse;
import br.com.chfb.api.model.Perfil;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper perfilMapper;

    // ======================
    // READ
    // ======================
    @Transactional(readOnly = true)
    public PerfilResponse obterPerfil() {

        Usuario usuario = getUsuarioLogadoComPerfil();

        if (usuario.getPerfil() == null) {
            throw new RuntimeException("Perfil não cadastrado");
        }

        return perfilMapper.toDTO(usuario.getPerfil());
    }

    // ======================
    // CREATE
    // ======================
    @Transactional
    public PerfilResponse criarPerfil(PerfilRequest request) {

        Usuario usuario = getUsuarioLogadoComPerfil();

        if (usuario.getPerfil() != null) {
            throw new RuntimeException("Perfil já existe");
        }

        Perfil perfil = perfilMapper.toEntity(request);
        perfil.setUsuario(usuario);

        usuario.setPerfil(perfil);

        return perfilMapper.toDTO(perfil);
    }

    // ======================
    // UPDATE
    // ======================
    @Transactional
    public PerfilResponse atualizarPerfil(PerfilRequest request) {

        Usuario usuario = getUsuarioLogadoComPerfil();

        Perfil perfilExistente = usuario.getPerfil();
        if (perfilExistente == null) {
            throw new RuntimeException("Perfil não existe");
        }

        // Atualização controlada (campos simples)
        perfilExistente.setNome(request.nome());
        perfilExistente.setSobrenome(request.sobrenome());
        perfilExistente.setDataNascimento(request.dataNascimento());
        perfilExistente.setCpf(request.cpf());
        perfilExistente.setRg(request.rg());

        return perfilMapper.toDTO(perfilExistente);
    }

    // ======================
    // DELETE
    // ======================
    @Transactional
    public void removerPerfil() {

        Usuario usuario = getUsuarioLogadoComPerfil();

        if (usuario.getPerfil() == null) {
            return;
        }

        usuario.setPerfil(null);
    }

    // ======================
    // UTIL
    // ======================
    private Usuario getUsuarioLogadoComPerfil() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository
                .findByUsernameWithProfile(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
