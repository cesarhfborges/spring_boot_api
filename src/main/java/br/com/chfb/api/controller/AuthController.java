package br.com.chfb.api.controller;

import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password
    ) {

        var usuario = this.repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário inválido"));

        if (!this.encoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return this.jwtService.generateToken(username);
    }

    @PostMapping("/logout")
    public void logout() {
        // Stateless: logout é responsabilidade do cliente
    }
}
