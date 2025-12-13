package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.LoginRequest;
import br.com.chfb.api.dto.resp.LoginResponse;
import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login e logout")
public class AuthController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna um JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {

        var usuario = this.repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário inválido"));

        if (!this.encoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

//        return this.jwtService.generateToken(request.username());
        String token = jwtService.generateToken(request.getUsername());
        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationDateTime().toString()
        );
    }

    @Operation(
            summary = "Logout",
            description = "Logout stateless. Deve ser tratado pelo cliente."
    )
    @PostMapping("/logout")
    public void logout() {
        // Stateless: logout é responsabilidade do cliente
    }
}
