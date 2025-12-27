package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/contatos")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "05 - Contatos", description = "CRUD de contatos do sistema")
public class ContatoController {

    private final ContatoService contatoService;

    @Operation(summary = "Listar")
    @GetMapping
    public Set<ContatoResponse> listar(@PathVariable Long funcionarioId) {
        return contatoService.listarTodos(funcionarioId);
    }

    @Operation(summary = "Cadastrar")
    @PostMapping
    public ContatoResponse criar(@PathVariable Long funcionarioId, @RequestBody ContatoRequest request) {
        return contatoService.criar(funcionarioId, request);
    }

    @Operation(summary = "Mostrar")
    @GetMapping("/{id}")
    public ContatoResponse mostrar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id
    ) {
        return contatoService.buscarPorId(funcionarioId, id);
    }

    @Operation(summary = "Atualizar")
    @PutMapping("/{id}")
    public ContatoResponse atualizar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id,
            @RequestBody ContatoRequest request
    ) {
        return contatoService.atualizar(funcionarioId, id, request);
    }

    @Operation(summary = "Remover")
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long funcionarioId, @PathVariable Long id) {
        contatoService.remover(funcionarioId, id);
    }
}
