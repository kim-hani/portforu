package org.pinggu.portforu.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter JWT token with **Bearer &lt;token&gt;**"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        String schemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Portforu API")
                        .version("v1")
                        .description("Portforu 서비스 API 명세")
                )
                .components(new Components()
                        .addSecuritySchemes(schemeName,
                                new io.swagger.v3.oas.models.security.SecurityScheme()       // ← fully-qualified
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                )
                .addSecurityItem(
                        new io.swagger.v3.oas.models.security.SecurityRequirement()     // ← fully-qualified
                                .addList(schemeName)
                );
    }
}
