package br.com.chfb.api.dto.req;

import java.time.LocalDate;
import java.util.Set;

public record FuncionarioRequest(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String rg,
        Set<EnderecoRequest> enderecos,
        Set<ContatoRequest> contatos
) {
}
