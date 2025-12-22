package br.com.chfb.api.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Resposta padrão de erro da API")
public class ApiErrorResponse {

    @Schema(example = "unauthorized")
    private String message;

    @Schema(example = "401")
    private int status;
}
