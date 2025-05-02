package org.pinggu.portforu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server-url:https://api.portforu.online}")
    private String serverUrl;

    @Bean
    public OpenAPI openApi() {
        String schemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Portforu API")
                        .version("v1")
                        .description("Portforu 서비스 API 명세"))
                .servers(List.of(new Server().url(serverUrl).description("환경별 서버")))
                .components(new Components()
                        .addSecuritySchemes(schemeName,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                        .name("Authorization")))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                        .addList(schemeName));
    }
}
