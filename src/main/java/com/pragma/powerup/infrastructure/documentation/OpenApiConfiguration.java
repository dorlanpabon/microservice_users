package com.pragma.powerup.infrastructure.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi(@Value("${appdescription}") String appDescription,
                                 @Value("${appversion}") String appVersion){
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("Hexagonal Microservices Users API")
                .version(appVersion)
                .description(appDescription)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            )
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth")) // 🔒 Se requiere autenticación
                .components(new Components().addSecuritySchemes("BearerAuth",
                        securityScheme()));
    }
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("BearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}