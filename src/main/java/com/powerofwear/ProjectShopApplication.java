package com.powerofwear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ProjectShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectShopApplication.class, args);
    }
}
