package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.service.ContatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/perfil/contatos")
@RequiredArgsConstructor
@Tag(name = "04 - Contatos")
public class ContatoController {

    private final ContatoService contatoService;

    @GetMapping
    public Set<ContatoResponse> listar() {
        return contatoService.listarContatos();
    }

    @PostMapping
    public ContatoResponse criar(@RequestBody ContatoRequest request) {
        return contatoService.criar(request);
    }

    @PutMapping("/{id}")
    public ContatoResponse atualizar(
            @PathVariable Long id,
            @RequestBody ContatoRequest request
    ) {
        return contatoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        contatoService.remover(id);
    }
}
