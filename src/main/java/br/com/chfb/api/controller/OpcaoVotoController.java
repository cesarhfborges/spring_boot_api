package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.OpcaoVotoMapper;
import br.com.chfb.api.dto.req.OpcaoVotoRequest;
import br.com.chfb.api.dto.resp.OpcaoVotoResponse;
import br.com.chfb.api.security.annotation.PodeGerenciar;
import br.com.chfb.api.service.OpcaoVotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunioes/{reuniaoId}/pautas/{pautaId}/opcoes")
@RequiredArgsConstructor
@PodeGerenciar
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "09 - Opções", description = "Configuração de opções de voto")
public class OpcaoVotoController {

    private final OpcaoVotoService service;
    private final OpcaoVotoMapper mapper;

    @PostMapping
    @Operation(summary = "Cadastrar opção de voto")
    public OpcaoVotoResponse criar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @RequestBody @Valid OpcaoVotoRequest request
    ) {
        return mapper.toDTO(
                service.salvar(
                        reuniaoId,
                        pautaId,
                        mapper.toEntity(request)
                )
        );
    }

    @GetMapping
    @Operation(summary = "Listar opções de voto da pauta")
    public List<OpcaoVotoResponse> listar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId
    ) {
        return service.listarPorPauta(reuniaoId, pautaId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar opção de voto por ID")
    public OpcaoVotoResponse buscarPorId(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id
    ) {
        return mapper.toDTO(
                service.buscarPorId(reuniaoId, pautaId, id)
        );
    }

    /* ========================= UPDATE ========================= */

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar opção de voto")
    public OpcaoVotoResponse atualizar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id,
            @RequestBody @Valid OpcaoVotoRequest request
    ) {
        return mapper.toDTO(
                service.atualizar(
                        reuniaoId,
                        pautaId,
                        id,
                        mapper.toEntity(request)
                )
        );
    }

    /* ========================= DELETE ========================= */

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir opção de voto")
    public void excluir(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id
    ) {
        service.excluir(reuniaoId, pautaId, id);
    }
}
