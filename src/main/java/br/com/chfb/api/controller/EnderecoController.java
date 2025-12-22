package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/enderecos")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "04 - Endereços", description = "CRUD de endereços do sistema")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Listar")
    @GetMapping
    public Set<EnderecoResponse> listar(@PathVariable Long funcionarioId) {
        return enderecoService.listarTodos(funcionarioId);
    }

    @Operation(summary = "Cadastrar")
    @PostMapping
    public EnderecoResponse criar(
            @PathVariable Long funcionarioId,
            @RequestBody EnderecoRequest request
    ) {
        return enderecoService.criar(funcionarioId, request);
    }

    @Operation(summary = "Mostrar")
    @GetMapping("/{id}")
    public EnderecoResponse mostrar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id
    ) {
        return enderecoService.buscarPorId(funcionarioId, id);
    }

    @Operation(summary = "Atualizar")
    @PutMapping("/{id}")
    public EnderecoResponse atualizar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id,
            @RequestBody EnderecoRequest request
    ) {
        return enderecoService.atualizar(funcionarioId, id, request);
    }

    @Operation(summary = "Remover")
    @DeleteMapping("/{id}")
    public void remover(
            @PathVariable Long funcionarioId,
            @PathVariable Long id
    ) {
        enderecoService.remover(funcionarioId, id);
    }
}