package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.VotoMapper;
import br.com.chfb.api.dto.req.VotoRequest;
import br.com.chfb.api.dto.resp.VotoResponse;
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
@RequestMapping("/api/reunioes/{reuniaoId}/pautas/{pautaId}/votos")
@RequiredArgsConstructor
@Tag(name = "10 - Voto", description = "")
@SecurityRequirement(name = "bearerAuth")
public class VotoController {

    private final VotoService service;
    private final VotoMapper mapper;

    @PostMapping
    @PodeVotar
    @VerificarBloqueioVoto
    @Operation(summary = "Registrar voto")
    public VotoResponse votar(
            @PathVariable Long reuniaoId,
            @PathVariable Long pautaId,
            @RequestBody @Valid VotoRequest request
    ) {
        return mapper.toDTO(
                service.salvar(
                        reuniaoId,
                        pautaId,
                        mapper.toEntity(request)
                )
        );
    }
}