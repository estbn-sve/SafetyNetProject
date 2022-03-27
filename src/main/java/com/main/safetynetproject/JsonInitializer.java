package com.main.safetynetproject;

import com.main.safetynetproject.json.JsonFileConvert;
import com.main.safetynetproject.service.FireStationService;
import com.main.safetynetproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class JsonInitializer implements CommandLineRunner {

    @Value("${jsonFile}")
    private Resource jsonDataFile;

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationsService;

    @Override
    public void run(String... args) {
        JsonFileConvert startJsonFileConvert = new JsonFileConvert(jsonDataFile);
        startJsonFileConvert.extractJsonData();
        personService.addAllPersons(startJsonFileConvert.getPersonList());
        fireStationsService.addAllFireStations(startJsonFileConvert.getFireStationsList());
    }
}
