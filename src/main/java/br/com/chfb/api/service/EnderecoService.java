package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.EnderecoMapper;
import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.model.Endereco;
import br.com.chfb.api.model.Perfil;
import br.com.chfb.api.model.UF;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.EnderecoRepository;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    // ======================
    // LIST
    // ======================
    @Transactional(readOnly = true)
    public Set<EnderecoResponse> listarEnderecos() {

        Perfil perfil = getPerfilUsuarioLogado();

        return perfil.getEnderecos()
                .stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toSet());
    }

    // ======================
    // CREATE
    // ======================
    @Transactional
    public EnderecoResponse criar(EnderecoRequest request) {

        Perfil perfil = getPerfilUsuarioLogado();

        Endereco endereco = enderecoMapper.toEntity(request);
        endereco.setPerfil(perfil);

        enderecoRepository.save(endereco);

        return enderecoMapper.toDTO(endereco);
    }

    // ======================
    // UPDATE
    // ======================
    @Transactional
    public EnderecoResponse atualizar(Long id, EnderecoRequest request) {

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        validarPertencimento(endereco);

        endereco.setLogradouro(request.logradouro());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setUf(UF.valueOf(request.uf()));
        endereco.setCep(request.cep());

        return enderecoMapper.toDTO(endereco);
    }

    // ======================
    // DELETE
    // ======================
    @Transactional
    public void remover(Long id) {

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        validarPertencimento(endereco);

        enderecoRepository.delete(endereco);
    }

    // ======================
    // UTIL
    // ======================
    private Perfil getPerfilUsuarioLogado() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository
                .findByUsernameWithProfile(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getPerfil() == null) {
            throw new RuntimeException("Perfil não cadastrado");
        }

        return usuario.getPerfil();
    }

    private void validarPertencimento(Endereco endereco) {
        if (!endereco.getPerfil().getId().equals(getPerfilUsuarioLogado().getId())) {
            throw new RuntimeException("Acesso negado ao endereço");
        }
    }
}
