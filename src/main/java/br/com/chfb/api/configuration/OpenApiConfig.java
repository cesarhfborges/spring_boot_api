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
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.List;

@Configuration
public class OpenApiConfig {

    private final List<Tag> tags = List.of(new Tag().name("01 - Autenticação").description("Endpoints de login e logout"),

            new Tag().name("02 - Perfil").description("Atualização de perfil"),

            new Tag().name("03 - Funcionario").description("CRUD de funcionarios do sistema"),

            new Tag().name("04 - Endereços").description("CRUD de endereços do sistema"),

            new Tag().name("05 - Contatos").description("CRUD de contatos do sistema"),

            new Tag().name("06 - Reuniao").description("Gestão de reuniões"),

            new Tag().name("07 - Pautas").description("Gestão de pautas da reunião"),

            new Tag().name("08 - Bloqueio").description("Bloqueio de voto por pauta"),

            new Tag().name("09 - Opções").description("Configuração de opções de voto"),

            new Tag().name("10 - Votação").description("Registro de votos"));

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema de Autenticação")
                        .description("API REST com Spring Boot, JWT e Swagger")
                        .version("1.0.0")
                        .description("""
                                API REST com Spring Boot, JWT e Swagger.
                                
                                ### 📌 Exemplo de script para Insomnia (pós-request)
                                ```javascript
                                const jsonData = insomnia.response.json();
                                const token = jsonData.auth_token;
                                
                                if (token) {
                                    insomnia.environment.set("bearerToken", token);
                                }
                                ```
                                
                                ### 📌 Exemplo de script para Postman (Tests)
                                ```javascript
                                const responseJson = pm.response.json();
                                
                                pm.test("Response status is 200 OK", function () {
                                    pm.response.to.have.status(200);
                                });
                                
                                if (responseJson.access_token) {
                                    pm.environment.set("api_token", responseJson.access_token);
                                    console.log("api_token set to: " + pm.environment.get("api_token"));
                                }
                                ```
                                
                                ### 🔐 Autenticação
                                Header esperado nas requisições protegidas:
                                ```
                                Authorization: Bearer {{token}}
                                ```
                                """)
                ).tags(this.tags)
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

//    @Bean
//    public OpenApiCustomizer sortPathsByTag() {
//        return openApi -> {
//
//            if (openApi.getPaths() == null || openApi.getPaths().isEmpty()) {
//                return;
//            }
//
//            var sortedEntries = openApi.getPaths().entrySet()
//                    .stream()
//                    .sorted(Comparator.comparing(entry -> {
//                        var pathItem = entry.getValue();
//
//                        if (pathItem.readOperations() != null && !pathItem.readOperations().isEmpty()) {
//                            var operation = pathItem.readOperations().get(0);
//                            if (operation.getTags() != null && !operation.getTags().isEmpty()) {
//                                return operation.getTags().get(0);
//                            }
//                        }
//                        return "ZZZ";
//                    }))
//                    .toList();
//
//            var sortedPaths = new io.swagger.v3.oas.models.Paths();
//            sortedEntries.forEach(entry ->
//                    sortedPaths.put(entry.getKey(), entry.getValue())
//            );
//
//            openApi.setPaths(sortedPaths);
//        };
//    }

//    @Bean
//    public OpenApiCustomizer sortOperationsInsidePath() {
//        return openApi -> openApi.getPaths().values().forEach(pathItem ->
//                pathItem.readOperations().sort(
//                        Comparator
//                                .comparing(
//                                        (Operation op) -> op.getTags() != null && !op.getTags().isEmpty()
//                                                ? op.getTags().get(0)
//                                                : "ZZZ"
//                                )
//                                .thenComparing(op -> op.getOperationId() != null ? op.getOperationId() : "")
//                )
//        );
//    }

    @Bean
    public OpenApiCustomizer sortByTagOrder() {
        return openApi -> {

            if (openApi.getPaths() == null || openApi.getPaths().isEmpty()) {
                return;
            }

            /* =========================
             * 1. Ordenar PATHS por número da TAG
             * ========================= */
            var sortedPaths = openApi.getPaths().entrySet()
                    .stream()
                    .sorted(Comparator.comparing(entry -> {
                        var ops = entry.getValue().readOperations();
                        if (ops != null && !ops.isEmpty()) {
                            return extractTagOrder(ops.get(0));
                        }
                        return Integer.MAX_VALUE;
                    })).collect(io.swagger.v3.oas.models.Paths::new, (paths, entry) -> paths.put(entry.getKey(), entry.getValue()), io.swagger.v3.oas.models.Paths::putAll);

            openApi.setPaths(sortedPaths);

            /* =========================
             * 2. Ordenar TAGS globais (ESSENCIAL)
             * ========================= */
            if (openApi.getTags() != null) {
                openApi.setTags(openApi.getTags().stream().sorted(Comparator.comparing(tag -> {
                    try {
                        String number = tag.getName().split("-")[0].trim();
                        return Integer.parseInt(number);
                    } catch (Exception e) {
                        return Integer.MAX_VALUE;
                    }
                })).toList());
            }
        };
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

    private int extractTagOrder(Operation operation) {
        if (operation.getTags() == null || operation.getTags().isEmpty()) {
            return Integer.MAX_VALUE;
        }

        try {
            String number = operation.getTags().get(0).split("-")[0].trim();
            return Integer.parseInt(number);
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }
}
