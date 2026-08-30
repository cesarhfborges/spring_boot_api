package br.com.chfb.api.service;

import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuarioRepository repository;

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }
}
