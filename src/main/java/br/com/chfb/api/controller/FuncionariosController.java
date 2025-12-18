package br.com.chfb.api.controller;

import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "02 - Funcionario", description = "CRUD de funcionarios do sistema")
@RequiredArgsConstructor
@EnableMethodSecurity
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionariosController {

    private final FuncionarioService service;

    @Operation(
            summary = "Listar",
            description = "Lista todos os usuários"
    )
    @GetMapping
    public Set<FuncionarioResponse> listar() {
        return this.service.listarTodos();
    }
}
