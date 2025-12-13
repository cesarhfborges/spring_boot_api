package br.com.chfb.api.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
    private String auth_token;

    @Schema(example = "Bearer")
    private String type;

    @Schema(example = "2025-12-13")
    private String expires_in;

}