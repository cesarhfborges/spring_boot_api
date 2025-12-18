package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.service.ContatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Tag(name = "04 - Contatos", description = "CRUD de contatos do sistema")
@RequiredArgsConstructor
@EnableMethodSecurity
@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    @GetMapping
    public Set<ContatoResponse> listar(@PathVariable Long funcionarioId) {
        return contatoService.listarTodos(funcionarioId);
    }

    @PostMapping
    public ContatoResponse criar(@PathVariable Long funcionarioId, @RequestBody ContatoRequest request) {
        return contatoService.criar(funcionarioId, request);
    }

    @PutMapping("/{id}")
    public ContatoResponse atualizar(
            @PathVariable Long funcionarioId,
            @PathVariable Long id,
            @RequestBody ContatoRequest request
    ) {
        return contatoService.atualizar(funcionarioId, id, request);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long funcionarioId, @PathVariable Long id) {
        contatoService.remover(funcionarioId, id);
    }
}
