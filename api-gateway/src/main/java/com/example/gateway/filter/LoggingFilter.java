package com.example.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import java.util.UUID;

@Component
public class LoggingFilter implements GlobalFilter {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String traceId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();

        log.info("Incoming Request -> Method: {} | Path: {} | TraceId: {}",
                method, path, traceId);

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    long duration = System.currentTimeMillis() - startTime;

                    log.info("Outgoing Response -> Status: {} | Duration: {} ms | TraceId: {}",
                            exchange.getResponse().getStatusCode(),
                            duration,
                            traceId);
                });
    }
}
