package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()

                .route("product", r -> r
                        .path("/products/**")
                        .uri("lb://product-service"))

                .route("inventory", r -> r
                        .path("/inventory/**")
                        .uri("lb://inventory-service"))

                .route("auth", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))

                .build();
    }
}
