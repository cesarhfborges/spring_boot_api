package br.com.chfb.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

public record FuncionarioRequest(
        @Schema(example = "Guilherme")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema(example = "Silva")
        @NotBlank(message = "Sobrenome é obrigatório")
        String sobrenome,

        @Schema(example = "2020-01-01")
        @NotNull(message = "Data de nascimento é obrigatória")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento,

        @Schema(example = "00000000000")
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(
                regexp = "\\d{11}",
                message = "CPF deve conter exatamente 11 dígitos numéricos"
        )
        String cpf,

        @Schema(example = "0000000")
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
