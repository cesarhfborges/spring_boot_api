package br.com.chfb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDTO(
        @Schema(example = "admin")
        String username,

        @Schema(example = "admin123")
        String password
) {
}
