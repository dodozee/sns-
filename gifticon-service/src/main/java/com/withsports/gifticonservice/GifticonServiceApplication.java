package com.withsports.gifticonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.codec.ServerCodecConfigurer;


@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class GifticonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GifticonServiceApplication.class, args);
    }
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }

}