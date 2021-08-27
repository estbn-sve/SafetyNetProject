package com.main.safetynetproject.app;

import com.main.safetynetproject.json.JsonFileConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetProjectApplication.class, args);
    }

    @Override
    public void run(String... args){
        JsonFileConvert startJsonFileConvert = new JsonFileConvert();
        startJsonFileConvert.ExecuteJsonFileConvert();
    }
}
