package com.torneofutbol.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Torneo Futbol API",
                version = "1.0.0",
                description = "API REST para gestionar un campeonato de futbol todos contra todos"
        )
)
public class OpenApiConfig {
}
