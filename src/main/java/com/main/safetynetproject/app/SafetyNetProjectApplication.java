package com.main.safetynetproject.app;

import com.main.safetynetproject.json.JsonFileConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class SafetyNetProjectApplication implements CommandLineRunner {

    @Value("${jsonFile}")
    private Resource jsonDataFile;

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetProjectApplication.class, args);
    }

    @Override
    public void run(String... args){
        JsonFileConvert startJsonFileConvert = new JsonFileConvert(jsonDataFile);
        startJsonFileConvert.extractJsonData();
    }
}
