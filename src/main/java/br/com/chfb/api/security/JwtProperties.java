package br.com.chfb.api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.security.token")
public class JwtProperties {

    private String secret;
    private Long expirationTime;
}
