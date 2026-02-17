package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;

@SpringBootApplication
public class InventoryServiceApplication {
	
    private final Environment environment;

    public InventoryServiceApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @PostConstruct
    public void checkProfile() {
        System.out.println("ACTIVE PROFILE: "
                + Arrays.toString(environment.getActiveProfiles()));
    }

}
