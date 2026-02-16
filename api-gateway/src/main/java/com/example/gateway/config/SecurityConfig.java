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

                        // AUTH SERVICE (public)
                        .pathMatchers("/api/v1/auth/**").permitAll()

                        // PRODUCT SERVICE
                        .pathMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**")
                                .hasAnyRole("USER", "ADMIN")

                        // INVENTORY SERVICE
                        .pathMatchers("/api/v1/inventory/**").hasRole("ADMIN")

                        // ORDER SERVICE
                        .pathMatchers(HttpMethod.POST, "/api/v1/orders/**")
                                .hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/orders/**")
                                .hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/orders/**")
                                .hasRole("ADMIN")

                        // AGGREGATOR SERVICE
                        .pathMatchers("/api/v1/aggregate/**").hasAnyRole("USER", "ADMIN")


                        .anyExchange().authenticated()
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
