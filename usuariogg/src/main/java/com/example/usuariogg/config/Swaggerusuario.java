package com.example.usuariogg.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class Swaggerusuario {
    @Bean
    public OpenAPI customOpenAPI() {
        final String nombreEsquemaSeguridad = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("API de Usuarios GG - Monsoon")
                        .version("1.0")
                        .description("Documentación interactiva de la API con soporte para JWT y HATEOAS"))
                
                .addSecurityItem(new SecurityRequirement().addList(nombreEsquemaSeguridad))
                .components(new Components()
                        .addSecuritySchemes(nombreEsquemaSeguridad,
                                new SecurityScheme()
                                        .name(nombreEsquemaSeguridad)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}


