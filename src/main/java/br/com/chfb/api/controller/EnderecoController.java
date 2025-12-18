package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.service.EnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Tag(name = "03 - Endereços", description = "CRUD de endereços do sistema")
@RequiredArgsConstructor
@EnableMethodSecurity
@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public Set<EnderecoResponse> listar(@PathVariable Long funcionarioId) {
        return enderecoService.listarTodos(funcionarioId);
    }

    @PostMapping
    public EnderecoResponse criar(
            @PathVariable Long funcionarioId,
            @RequestBody EnderecoRequest request
    ) {
        return enderecoService.criar(funcionarioId, request);
    }

    @PutMapping("/{id}")
    public EnderecoResponse atualizar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id,
            @RequestBody EnderecoRequest request
    ) {
        return enderecoService.atualizar(funcionarioId, id, request);
    }

    @DeleteMapping("/{id}")
    public void remover(
            @PathVariable Long funcionarioId,
            @PathVariable Long id
    ) {
        enderecoService.remover(funcionarioId, id);
    }
}