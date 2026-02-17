package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

                        .pathMatchers("/api/v1/auth/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**")
                                .hasAnyRole("USER", "ADMIN")

                        .pathMatchers("/api/v1/inventory/**").hasRole("ADMIN")

                        .pathMatchers(HttpMethod.POST, "/api/v1/orders/**")
                                .hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/orders/**")
                                .hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/orders/**")
                                .hasRole("ADMIN")

                        .pathMatchers("/api/v1/aggregate/**")
                                .hasAnyRole("USER", "ADMIN")

                        .anyExchange().authenticated()
                )
                .httpBasic() // 👈 Enables custom username/password
                .and()
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {

        UserDetails admin = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(admin);
    }
}
