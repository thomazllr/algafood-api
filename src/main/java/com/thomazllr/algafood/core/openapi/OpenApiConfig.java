package com.thomazllr.algafood.core.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Algafood API", version = "v1", description = "API REST de um sistema de gerenciamento de restaurantes.",
        contact = @Contact(name = "Guilherme Thomaz", url = "https://thomazllr.me")))
public class OpenApiConfig {


}
