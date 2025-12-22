package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.service.PerfilService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "02 - Perfil", description = "atualização de perfil.")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public FuncionarioResponse obterPerfil() {
        return perfilService.obterPerfil();
    }

    @PutMapping
    public FuncionarioResponse atualizarPerfil(
            @Valid @RequestBody FuncionarioRequest request
    ) {
        return perfilService.atualizarPerfil(request);
    }
}
