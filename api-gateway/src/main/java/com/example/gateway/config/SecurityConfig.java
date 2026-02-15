package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(csrf -> csrf.disable())
                    .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/products/**")
                            .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/products/**")
                            .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/products/**")
                            .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/products/**")
                            .hasAnyRole("USER", "ADMIN")
                            .anyExchange().authenticated()
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
