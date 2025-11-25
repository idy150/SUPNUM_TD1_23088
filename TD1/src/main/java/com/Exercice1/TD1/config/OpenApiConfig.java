package com.Exercice1.TD1.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                    .title("Server Management API")
                    .version("1.0")
                    .description("API for managing and monitoring servers in the data center.")
                    .contact(new Contact()
                        .name("SUPNUM_TD1")
                        .email("support@example.com"))
                    .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"))
                );
    }
}
