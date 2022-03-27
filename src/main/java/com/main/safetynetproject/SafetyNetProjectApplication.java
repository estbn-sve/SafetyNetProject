package com.main.safetynetproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Mise en place des endpoints REST OK
Mise en place d'actuators : info, health, httptrace, metrics OK
Logger tous les endpoints OK
Tout tester : Rapport de couverture et de tests OK
Mise en place des Urls
Test URL
 */
@SpringBootApplication
public class SafetyNetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetProjectApplication.class, args);
    }

}
