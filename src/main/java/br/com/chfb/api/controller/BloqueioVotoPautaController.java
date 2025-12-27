package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.BloqueioVotoPautaMapper;
import br.com.chfb.api.dto.req.BloqueioVotoPautaRequest;
import br.com.chfb.api.dto.resp.BloqueioVotoPautaResponse;
import br.com.chfb.api.model.BloqueioVotoPauta;
import br.com.chfb.api.security.annotation.PodeGerenciar;
import br.com.chfb.api.service.BloqueioVotoPautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reunioes/{reuniaoId}/pautas/{pautaId}/bloqueios-voto")
@RequiredArgsConstructor
@PodeGerenciar
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "08 - Bloqueio", description = "")
public class BloqueioVotoPautaController {

    private final BloqueioVotoPautaService service;
    private final BloqueioVotoPautaMapper mapper;

    @PostMapping
    @Operation(summary = "Cadastrar")
    public BloqueioVotoPautaResponse criar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @RequestBody @Valid BloqueioVotoPautaRequest request
    ) {
        BloqueioVotoPauta entity = mapper.toEntity(request);

        return mapper.toDTO(
                service.salvar(
                        reuniaoId,
                        pautaId,
                        entity,
                        request.funcionarioId()
                )
        );
    }

    @GetMapping
    @Operation(summary = "Listar bloqueios da pauta")
    public List<BloqueioVotoPautaResponse> listar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId
    ) {
        return service.listarPorPauta(reuniaoId, pautaId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bloqueio por ID")
    public BloqueioVotoPautaResponse buscarPorId(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id
    ) {
        return mapper.toDTO(
                service.buscarPorId(reuniaoId, pautaId, id)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar bloqueio de voto")
    public BloqueioVotoPautaResponse atualizar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id,
            @RequestBody @Valid BloqueioVotoPautaRequest request
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover bloqueio de voto")
    public void excluir(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @PathVariable Long id
    ) {
        service.excluir(reuniaoId, pautaId, id);
    }
}
