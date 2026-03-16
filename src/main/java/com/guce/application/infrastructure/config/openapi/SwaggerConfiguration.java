package com.guce.application.infrastructure.config.openapi;

import com.guce.application.infrastructure.util.InfraConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "GUCE Application Service API", version = "${info.app.version}", description = "GUCE Application Service API"))
public class SwaggerConfiguration {
    public static final String JWT_AUTHORIZATION = "jwt";
    public static final String OIDC_SECURITY_SCHEME = "oidcScheme";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().components(
                new Components()
                        .addSecuritySchemes(JWT_AUTHORIZATION,
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .bearerFormat("JWT"))
                        .addSecuritySchemes(OIDC_SECURITY_SCHEME,
                                new SecurityScheme().type(Type.HTTP).scheme("Bearer").bearerFormat("JWT")))
                .addSecurityItem(
                        new SecurityRequirement().addList(OIDC_SECURITY_SCHEME)
                                .addList(InfraConstants.JWT_AUTHORIZATION))
                .info(new Info().title("GUCE Application Service API")
                        .description("This microservice is meant to manage Application Service for GUCE customers"));
    }
}
