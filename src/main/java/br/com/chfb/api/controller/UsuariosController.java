package br.com.chfb.api.controller;

import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @GetMapping
    public List<Usuario> listar() {
        return this.repository.findAll();
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return this.repository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = this.repository.findById(id).orElseThrow();
        existente.setUsername(usuario.getUsername());
        if (usuario.getPassword() != null) {
            existente.setPassword(encoder.encode(usuario.getPassword()));
        }
        return this.repository.save(existente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
