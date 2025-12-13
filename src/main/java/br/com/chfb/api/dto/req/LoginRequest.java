package br.com.chfb.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(example = "admin")
    private String username;

    @Schema(example = "admin123")
    private String password;
}
