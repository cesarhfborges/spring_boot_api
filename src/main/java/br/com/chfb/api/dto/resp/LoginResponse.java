package br.com.chfb.api.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String auth_token;
    private String type;
    private String expires_in;
}
