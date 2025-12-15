package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.PerfilRequest;
import br.com.chfb.api.dto.resp.PerfilResponse;
import br.com.chfb.api.service.PerfilService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
@Tag(name = "02 - Perfil")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public PerfilResponse obter() {
        return perfilService.obterPerfil();
    }

    @PostMapping
    public PerfilResponse criar(@RequestBody PerfilRequest request) {
        return perfilService.criarPerfil(request);
    }

    @PutMapping
    public PerfilResponse atualizar(@RequestBody PerfilRequest request) {
        return perfilService.atualizarPerfil(request);
    }

//    // ======================
//    // DELETE
//    // ======================
//    @DeleteMapping
//    public void remover() {
//        perfilService.removerPerfil();
//    }
}
