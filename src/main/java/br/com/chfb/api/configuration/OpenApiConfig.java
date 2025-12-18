package br.com.chfb.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
//        SecurityScheme bearerAuth = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT");
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema de Autenticação")
                        .description("API REST com Spring Boot, JWT e Swagger")
                        .version("1.0.0")
                );
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//                .components(new Components()
//                        .addSecuritySchemes("bearerAuth", bearerAuth)
//                );
    }

    @Bean
    public OpenApiCustomizer sortOperations() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().sort(
                Comparator.comparing(Operation::getOperationId)
        ));
    }
}
