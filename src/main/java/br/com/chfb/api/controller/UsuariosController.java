package br.com.chfb.api.controller;

import br.com.chfb.api.dto.mapper.UsuarioMapper;
import br.com.chfb.api.dto.resp.UsuarioResponse;
import br.com.chfb.api.security.annotation.PodeGerenciar;
import br.com.chfb.api.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@PodeGerenciar
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "12 - Usuários", description = "")
public class UsuariosController {

    private final UsuariosService service;
    private final UsuarioMapper mapper;

    @GetMapping
    @Operation(summary = "Listar")
    public Set<UsuarioResponse> listaUsuarios() {
        return this.service.buscarTodos().stream().map(mapper::toDTO).collect(Collectors.toSet());
    }
}
