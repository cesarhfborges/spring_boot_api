package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.ReuniaoMapper;
import br.com.chfb.api.dto.req.ReuniaoRequest;
import br.com.chfb.api.dto.resp.ReuniaoResponse;
import br.com.chfb.api.model.Reuniao;
import br.com.chfb.api.security.annotation.PodeGerenciar;
import br.com.chfb.api.service.ReuniaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunioes")
@RequiredArgsConstructor
@PodeGerenciar
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "06 - Reuniao", description = "")
public class ReuniaoController {

    private final ReuniaoService service;
    private final ReuniaoMapper mapper;

    @GetMapping
    @Operation(summary = "Listar")
    public List<ReuniaoResponse> listar() {
        return service.buscarTodos().stream().map(mapper::toDTO).toList();
    }

    @PostMapping
    @Operation(summary = "Cadastrar")
    public ReuniaoResponse criar(@RequestBody @Valid ReuniaoRequest request) {
        Reuniao r = service.cadastrar(mapper.toEntity(request));
        return mapper.toDTO(r);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reunião por ID")
    public ReuniaoResponse buscarPorId(
            @PathVariable Long id
    ) {
        return mapper.toDTO(service.buscarPorId(id));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar reunião")
    public ReuniaoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ReuniaoRequest request
    ) {
        Reuniao reuniaoAtualizada = service.atualizar(
                id,
                mapper.toEntity(request)
        );
        return mapper.toDTO(reuniaoAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir reunião")
    public void excluir(
            @PathVariable Long id
    ) {
        service.excluir(id);
    }
}
