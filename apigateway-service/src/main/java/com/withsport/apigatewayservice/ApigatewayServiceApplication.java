package com.withsport.apigatewayservice;

import com.withsport.apigatewayservice.handler.GlobalExceptionHandler;
import jakarta.annotation.PostConstruct;
import org.apache.http.HttpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.TimeZone;

@SpringBootApplication
public class ApigatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApigatewayServiceApplication.class, args);
    }


}