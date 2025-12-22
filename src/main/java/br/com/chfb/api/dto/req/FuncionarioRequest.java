package br.com.chfb.api.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

public record FuncionarioRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Sobrenome é obrigatório")
        String sobrenome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(
                regexp = "\\d{11}",
                message = "CPF deve conter exatamente 11 dígitos numéricos"
        )
        String cpf,

        @NotBlank(message = "RG é obrigatório")
        @Pattern(
                regexp = "\\d{7,20}",
                message = "RG deve conter apenas números"
        )
        String rg
) {
    @JsonIgnore
    @AssertTrue(message = "Funcionário deve ter no mínimo 18 anos")
    public boolean isMaiorDeIdade() {
        if (dataNascimento == null) {
            return false;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears() >= 18;
    }
}
