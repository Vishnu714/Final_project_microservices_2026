package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import java.util.Arrays;

@SpringBootApplication
public class OrderServiceApplication {
	
    private final Environment environment;

    public OrderServiceApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @PostConstruct
    public void checkProfile() {
        System.out.println("ACTIVE PROFILE: "
                + Arrays.toString(environment.getActiveProfiles()));
    }

}