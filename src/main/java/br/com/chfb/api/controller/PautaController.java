package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.PautaMapper;
import br.com.chfb.api.dto.req.PautaRequest;
import br.com.chfb.api.dto.resp.PautaResponse;
import br.com.chfb.api.model.Pauta;
import br.com.chfb.api.security.annotation.PodeGerenciar;
import br.com.chfb.api.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunioes/{reuniaoId}/pautas")
@RequiredArgsConstructor
@PodeGerenciar
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "07 - Pautas", description = "")
public class PautaController {

    private final PautaService service;
    private final PautaMapper mapper;

    @GetMapping
    @Operation(summary = "Listar pautas da reunião")
    public List<PautaResponse> listar(
            @PathVariable Long reuniaoId
    ) {
        return service.listarPorReuniao(reuniaoId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @PostMapping
    @Operation(summary = "Cadastrar pauta")
    public PautaResponse criar(
            @PathVariable Long reuniaoId,
            @RequestBody @Valid PautaRequest request
    ) {
        return mapper.toDTO(
                service.salvar(reuniaoId, mapper.toEntity(request))
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pauta por ID")
    public PautaResponse buscar(
            @PathVariable Long reuniaoId,
            @PathVariable Long id
    ) {
        return mapper.toDTO(
                service.buscarPorId(reuniaoId, id)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pauta")
    public PautaResponse atualizar(
            @PathVariable Long reuniaoId,
            @PathVariable Long id,
            @RequestBody @Valid PautaRequest request
    ) {
        Pauta p = service.atualizar(
                reuniaoId,
                id,
                mapper.toEntity(request)
        );
        return mapper.toDTO(p);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pauta")
    public void excluir(
            @PathVariable Long reuniaoId,
            @PathVariable Long id
    ) {
        service.excluir(reuniaoId, id);
    }
}
