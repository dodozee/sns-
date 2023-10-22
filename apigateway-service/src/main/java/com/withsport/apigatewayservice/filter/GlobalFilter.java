package com.withsport.apigatewayservice.filter;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global com.example.scg.filter baseMessage: {}", config.getBaseMessage());
            System.out.println("555555555555");
            //Global pre Filter
            if (config.isPreLogger()) {
                log.info("Global Filter Start : request id -> {}", request.getId());
                log.info("Global Filter Start : request path -> {}", request.getPath());
            }

            //Global post Filter
            //webflux에서 단일값 전송할 때 Mono값으로 전송
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if (config.isPostLogger()) {
                    log.info("Global Filter End: response statuscode -> {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
