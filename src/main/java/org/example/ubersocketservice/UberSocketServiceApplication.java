package org.example.ubersocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.uberprojectentityservice.models")
public class UberSocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberSocketServiceApplication.class, args);
    }

}
