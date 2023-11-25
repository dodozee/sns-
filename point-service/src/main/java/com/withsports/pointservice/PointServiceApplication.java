package com.withsports.pointservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.codec.ServerCodecConfigurer;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class PointServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PointServiceApplication.class, args);
    }


}