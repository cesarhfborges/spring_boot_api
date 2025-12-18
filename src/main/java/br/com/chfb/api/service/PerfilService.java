package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.FuncionarioMapper;
import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.FuncionarioRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioLogadoProvider usuarioLogadoProvider;
    private final FuncionarioMapper funcionarioMapper;

    @Transactional(readOnly = true)
    public FuncionarioResponse obterPerfil() {

        Usuario usuario = usuarioLogadoProvider.getUsuarioLogado();

        Funcionario funcionario = funcionarioRepository
                .findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        return funcionarioMapper.toDTO(funcionario);
    }

    @Transactional
    public FuncionarioResponse atualizarPerfil(FuncionarioRequest request) {

        Usuario usuario = usuarioLogadoProvider.getUsuarioLogado();

        Funcionario funcionario = funcionarioRepository
                .findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        funcionario.setNome(request.nome());
        funcionario.setSobrenome(request.sobrenome());
        funcionario.setDataNascimento(request.dataNascimento());

        return funcionarioMapper.toDTO(funcionario);
    }
}
