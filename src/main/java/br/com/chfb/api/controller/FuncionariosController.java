package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/funcionarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "03 - Funcionario", description = "CRUD de funcionarios do sistema")
public class FuncionariosController {

    private final FuncionarioService service;

    @Operation(
            summary = "Listar",
            description = "Lista todos os funcionários"
    )
    @GetMapping
    public Set<FuncionarioResponse> listar(
            @RequestParam(required = false, defaultValue = "id", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "ASC", name = "direction") String direction
    ) {
        Set<String> camposPermitidos = Set.of("id", "nome", "sobrenome", "cpf");

        if (!camposPermitidos.contains(sort)) {
            sort = "id";
        }
        return service.listarTodos(sort, direction);
    }

    @Operation(
            summary = "Buscar por ID",
            description = "Busca um funcionário pelo ID"
    )
    @GetMapping("/{id}")
    public FuncionarioResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(
            summary = "Criar",
            description = "Cria um novo funcionário"
    )
    @PostMapping
    public FuncionarioResponse criar(@RequestBody FuncionarioRequest request) {
        return service.criar(request);
    }

    @Operation(
            summary = "Atualizar",
            description = "Atualiza um funcionário existente"
    )
    @PutMapping("/{id}")
    public FuncionarioResponse atualizar(
            @PathVariable Long id,
            @RequestBody FuncionarioRequest request
    ) {
        return service.atualizar(id, request);
    }

    @Operation(
            summary = "Remover",
            description = "Remove um funcionário"
    )
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
