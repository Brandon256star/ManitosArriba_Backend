package com.example.tp_grupo6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.tp_grupo6.entities")
@EnableJpaRepositories(basePackages = "com.example.tp_grupo6.repositories")
public class TpGrupo6Application {

    public static void main(String[] args) {
        SpringApplication.run(TpGrupo6Application.class, args);
    }

}
