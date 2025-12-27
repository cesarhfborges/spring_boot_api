package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.PautaMapper;
import br.com.chfb.api.dto.mapper.VotoMapper;
import br.com.chfb.api.dto.req.AbrirVotacaoRequest;
import br.com.chfb.api.dto.req.VotoRequest;
import br.com.chfb.api.dto.resp.PautaResponse;
import br.com.chfb.api.dto.resp.VotoResponse;
import br.com.chfb.api.model.Voto;
import br.com.chfb.api.security.annotation.PodeVotar;
import br.com.chfb.api.security.annotation.VerificarBloqueioVoto;
import br.com.chfb.api.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reunioes/{reuniaoId}/pautas/{pautaId}/votacao")
@RequiredArgsConstructor
@Tag(name = "10 - Votação", description = "")
@SecurityRequirement(name = "bearerAuth")
public class VotacaoController {

    private final VotoService service;
    private final VotoMapper votoMapper;
    private final PautaMapper pautaMapper;

    @PostMapping
    @PodeVotar
    @VerificarBloqueioVoto
    @Operation(summary = "Registrar voto")
    public VotoResponse votar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @RequestBody @Valid VotoRequest request
    ) {
        Voto voto = service.salvar(
                reuniaoId,
                pautaId,
                votoMapper.toEntity(request),
                request.votosComoLista(),
                request.codigoVoto()
        );
        return votoMapper.toDTO(voto);
    }

    @PostMapping("/abrir")
    @Operation(summary = "Abrir votação da pauta")
    public PautaResponse abrirVotacao(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @RequestBody @Valid AbrirVotacaoRequest request
    ) {
        return pautaMapper.toDTO(service.abrirVotacao(reuniaoId, pautaId, request));
    }

    @PostMapping("/encerrar")
    @Operation(summary = "Encerrar votação da pauta")
    public void encerrarVotacao(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId
    ) {
        service.encerrarVotacao(reuniaoId, pautaId);
    }

    @PostMapping("/cancelar")
    @Operation(summary = "Encerrar votação da pauta")
    public void cancelarVotacao(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId
    ) {
        service.cancelarVotacao(reuniaoId, pautaId);
    }
}