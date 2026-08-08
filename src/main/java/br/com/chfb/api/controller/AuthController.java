package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.LoginRequest;
import br.com.chfb.api.dto.req.RecuperarSenhaRequest;
import br.com.chfb.api.dto.resp.LoginResponse;
import br.com.chfb.api.dto.resp.LogoutResponse;
import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.security.JwtService;
import br.com.chfb.api.service.EmailServiceApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "01 - Autenticação", description = "Endpoints de login e logout")
public class AuthController {

    private final EmailServiceApi emailService;
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna um JWT",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {

        var usuario = this.repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário inválido"));

        if (!this.encoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<LogoutResponse> logout() {
        // Stateless: logout é responsabilidade do cliente
        return ResponseEntity.ok(
                new LogoutResponse("Logout efetuado com sucesso.")
        );
    }

    @Operation(
            summary = "Recuperar Senha",
            description = "Gera um token de recuperação e envia por e-mail via Mailpit"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> solicitarRedefinicao(@Valid @RequestBody RecuperarSenhaRequest request) {
        var usuario = this.repository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new RuntimeException("Usuário não encontrado com o e-mail informado")
                );

        String token = UUID.randomUUID().toString();

        // 3. TODO: Salvar o token no banco atrelado ao usuário (com uma data de expiração)
        // ex: tokenRepository.save(new PasswordResetToken(token, usuario, LocalDateTime.now().plusMinutes(15)));

        // 4. Dispara o e-mail para o Mailpit
        emailService.enviarLinkRedefinicao(usuario.getUsername(), token);

        return ResponseEntity.noContent().build();
    }
}
