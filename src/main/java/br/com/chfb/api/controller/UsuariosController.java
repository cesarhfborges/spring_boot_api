package br.com.chfb.api.controller;

import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "CRUD de usuários do sistema")
public class UsuariosController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public List<Usuario> listar() {
        return this.repository.findAll();
    }

    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return this.repository.save(usuario);
    }

    @Operation(summary = "Atualiza um usuário existente")
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = this.repository.findById(id).orElseThrow();
        existente.setUsername(usuario.getUsername());
        if (usuario.getPassword() != null) {
            existente.setPassword(encoder.encode(usuario.getPassword()));
        }
        return this.repository.save(existente);
    }

    @Operation(summary = "Remove um usuário")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
