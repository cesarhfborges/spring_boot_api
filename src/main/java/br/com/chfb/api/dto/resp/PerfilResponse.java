package br.com.chfb.api.dto.resp;

import java.time.LocalDate;
import java.util.Set;

public record PerfilResponse(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String rg,
        Set<EnderecoResponse> enderecos,
        Set<ContatoResponse> contatos
) {
}
