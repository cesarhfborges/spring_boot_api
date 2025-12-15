package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.service.EnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/perfil/enderecos")
@RequiredArgsConstructor
@Tag(name = "03 - Endereços")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public Set<EnderecoResponse> listar() {
        return enderecoService.listarEnderecos();
    }

    @PostMapping
    public EnderecoResponse criar(@RequestBody EnderecoRequest request) {
        return enderecoService.criar(request);
    }

    @PutMapping("/{id}")
    public EnderecoResponse atualizar(
            @PathVariable Long id,
            @RequestBody EnderecoRequest request
    ) {
        return enderecoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        enderecoService.remover(id);
    }
}