package br.com.chfb.api.dto.resp;

import java.time.LocalDate;

public record FuncionarioResponse(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String rg
) {
}
