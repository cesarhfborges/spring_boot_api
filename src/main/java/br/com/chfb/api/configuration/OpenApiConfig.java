package br.com.chfb.api.configuration;

import br.com.chfb.api.dto.resp.ApiErrorResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema de Autenticação")
                        .description("API REST com Spring Boot, JWT e Swagger")
                        .version("1.0.0")
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public OpenApiCustomizer sortOperations() {
        return openApi -> openApi.getPaths().values().forEach(
                pathItem -> pathItem.readOperations()
                        .sort(Comparator.comparing(Operation::getOperationId))
        );
    }

    @Bean
    public OpenApiCustomizer globalErrorResponsesCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {

                    operation.getResponses().addApiResponse("201",
                            buildErrorResponse("Created", "Cadastro efetuado com sucesso", 201));

                    operation.getResponses().addApiResponse("202",
                            buildErrorResponse("Accepted", "Recurso aceito", 202));

                    operation.getResponses().addApiResponse("400",
                            buildErrorResponse("Bad Request", "Problema na requisição", 400));

                    operation.getResponses().addApiResponse("401",
                            buildErrorResponse("Unauthorized", "Não autenticado", 401));

                    operation.getResponses().addApiResponse("403",
                            buildErrorResponse("Forbidden", "Acesso negado", 403));

                    operation.getResponses().addApiResponse("404",
                            buildErrorResponse("Not Found", "Recurso não encontrado", 404));

                    operation.getResponses().addApiResponse("405",
                            buildErrorResponse("Method Not Allowed", "Método não permitido", 405));

                    operation.getResponses().addApiResponse("406",
                            buildErrorResponse("Not Acceptable", "Não aceitável", 405));

                    operation.getResponses().addApiResponse("500",
                            buildErrorResponse("Internal Server Error", "Erro interno", 500));
                })
        );
    }

    private ApiResponse buildErrorResponse(String description, String message, int status) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(
                        "application/json",
                        new MediaType().example(
                                new ApiErrorResponse(message, status)
                        )
                ));
    }
}
