package br.com.chfb.api.dto.resp;

import java.time.LocalDate;

public record PerfilResponse(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String rg
) {
}
