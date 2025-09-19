package com.thomazllr.algafood.core.openapi;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Algafood API",
        version = "v1",
        description = "API REST de um sistema de gerenciamento de restaurantes.",
        contact = @Contact(name = "Guilherme Thomaz", url = "https://thomazllr.me")
))
public class OpenApiConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .externalDocs(new ExternalDocumentation()
                        .description("AlgaWorks")
                        .url("https://algaworks.com")
                ).tags(Arrays.asList(
                        new Tag().name("Cidades").description("Gerencia as cidades"),
                        new Tag().name("Grupos").description("Gerencia os grupos"),
                        new Tag().name("Cozinhas").description("Gerencia as cozinhas"),
                        new Tag().name("Formas de pagamento").description("Gerencia as formas de pagamento"),
                        new Tag().name("Pedidos").description("Gerencia os pedidos"),
                        new Tag().name("Restaurantes").description("Gerencia os restaurantes"),
                        new Tag().name("Estados").description("Gerencia os estados"),
                        new Tag().name("Produtos").description("Gerencia os produtos"),
                        new Tag().name("Usuários").description("Gerencia os usuários"),
                        new Tag().name("Permissões").description("Gerencia as permissões"),
                        new Tag().name("Estatísticas").description("Estatísticas da AlgaFood")
                )).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance()
                .read(org.springframework.http.ProblemDetail.class);

        schemaMap.putAll(problemSchema);
        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ProblemDetail")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }
}