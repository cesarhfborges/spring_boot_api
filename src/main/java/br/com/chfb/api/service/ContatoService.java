package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.ContatoMapper;
import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.model.Contato;
import br.com.chfb.api.model.Perfil;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.ContatoRepository;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final UsuarioRepository usuarioRepository;
    private final ContatoRepository contatoRepository;
    private final ContatoMapper contatoMapper;

    // ======================
    // LIST
    // ======================
    @Transactional(readOnly = true)
    public Set<ContatoResponse> listarContatos() {

        Perfil perfil = getPerfilUsuarioLogado();

        return perfil.getContatos()
                .stream()
                .map(contatoMapper::toDTO)
                .collect(Collectors.toSet());
    }

    // ======================
    // CREATE
    // ======================
    @Transactional
    public ContatoResponse criar(ContatoRequest request) {

        Perfil perfil = getPerfilUsuarioLogado();

        Contato contato = contatoMapper.toEntity(request);
        contato.setPerfil(perfil);

        contatoRepository.save(contato);

        return contatoMapper.toDTO(contato);
    }

    // ======================
    // UPDATE
    // ======================
    @Transactional
    public ContatoResponse atualizar(Long id, ContatoRequest request) {

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        validarPertencimento(contato);

        contato.setTipo(request.tipo());
        contato.setValor(request.valor());

        return contatoMapper.toDTO(contato);
    }

    // ======================
    // DELETE
    // ======================
    @Transactional
    public void remover(Long id) {

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        validarPertencimento(contato);

        contatoRepository.delete(contato);
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

    private void validarPertencimento(Contato contato) {
        if (!contato.getPerfil().getId().equals(getPerfilUsuarioLogado().getId())) {
            throw new RuntimeException("Acesso negado ao contato");
        }
    }
}
