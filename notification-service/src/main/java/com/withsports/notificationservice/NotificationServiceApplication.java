package com.withsports.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.codec.ServerCodecConfigurer;

@EnableFeignClients
@SpringBootApplication
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableJpaAuditing
public class NotificationServiceApplication{
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);

    }

}