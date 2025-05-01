package org.pinggu.portforu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration swaggerConfig = new CorsConfiguration();
        swaggerConfig.addAllowedOriginPattern("*");
        swaggerConfig.setAllowedMethods(List.of("GET", "OPTIONS"));
        swaggerConfig.addAllowedHeader("*");
        swaggerConfig.setAllowCredentials(false);

        CorsConfiguration apiConfig = new CorsConfiguration();
        apiConfig.addAllowedOrigin("http://localhost:3000");
        apiConfig.addAllowedOrigin("https://api.portforu.online");
        apiConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        apiConfig.addAllowedHeader("*");
        apiConfig.addExposedHeader("Authorization");
        apiConfig.addExposedHeader("refresh-token");
        apiConfig.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/v3/api-docs/**", swaggerConfig);
        source.registerCorsConfiguration("/swagger-ui/**", swaggerConfig);
        source.registerCorsConfiguration("/**", apiConfig);

        return new CorsFilter(source);
    }
}