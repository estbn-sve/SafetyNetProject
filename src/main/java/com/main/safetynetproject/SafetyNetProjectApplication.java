package com.main.safetynetproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Mise en place des endpoints REST
Mise en place des Urls
Mise en place d'actuators : Info, Health, HttpTrace, Metrics
Logger tous les endpoints
Tout tester : Rapport de couverture et de tests
 */
@SpringBootApplication
public class SafetyNetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetProjectApplication.class, args);
    }

}
