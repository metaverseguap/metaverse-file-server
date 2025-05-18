package com.metaverse.files.security.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * Класс конфигурации cors.
 *
 * В {@link SecurityConfig#securityFilterChain(HttpSecurity) SecurityConfig#securityFilterChain} отключены cors,
 * поэтому необходимо написать для них собственный конфигурационный класс.
 * В данном классе мы разрешим отправку запросов с посторонних адресов,
 * а не с того адреса, на котором запущен сервер
 *
 * @author Mikhail.Kataranov
 * @since 18.05.2025
 */
@Configuration
public class CorsConfig {

    private static final long MAX_AGE_SECONDS = 3600;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // cookie
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        config.setAllowedOriginPatterns(List.of("*"));

        config.setMaxAge(MAX_AGE_SECONDS);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
