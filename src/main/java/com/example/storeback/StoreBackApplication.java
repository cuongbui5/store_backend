package com.example.storeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class StoreBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreBackApplication.class, args);
    }

}
