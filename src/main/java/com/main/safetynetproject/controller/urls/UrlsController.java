package com.main.safetynetproject.controller.urls;

import com.main.safetynetproject.controller.urls.dto.EnfantsInAddressWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonAndFamilyInFireStationWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonAndFireStationWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonInFireStationWithCountResponse;
import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.FireStationRepository;
import com.main.safetynetproject.service.FireStationService;
import com.main.safetynetproject.service.MedicalRecordService;
import com.main.safetynetproject.service.PersonService;
import com.main.safetynetproject.service.urls.UrlsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UrlsController {
    @Autowired
    private UrlsService service;

    @GetMapping("/firestations")
    public PersonInFireStationWithCountResponse searchPersonFromFireStationResponsible(@RequestParam("stationNumber")final Integer stationNumber){
        log.info("GET /firestations?stationNumber="+stationNumber);
        return service.searchPersonFromFireStationResponsible(stationNumber);
    }

    @GetMapping("/childAlert")
    public EnfantsInAddressWithCountResponse searchEnfantFromAddressResponsible(@RequestParam("address")final String address){
    log.info("GET/ childAlert?address="+address);
    return service.searchEnfantFromAddressResponsible(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> searchNumberFromFireStationResponsible(@RequestParam("firestation_Number")final Integer stationNumber){
        log.info("GET /phoneAlert?stationNumber="+stationNumber);
        return service.searchNumberFromFireStationResponsible(stationNumber);
    }

    @GetMapping("/fire")
    public PersonAndFireStationWithCountResponse searchPersonAndFireStationFromAddressResponsible(@RequestParam("address")final String address){
        log.info("GET /fire?address="+address);
        return service.searchPersonAndFireStationFromAddressResponsible(address);
    }

    @GetMapping("/flood/stations")
    public PersonAndFamilyInFireStationWithCountResponse searchPersonAndFamilyFromFireStation(@RequestParam("stationNumber")final Integer stationNumber){
        log.info("GET /flood/stations?stations="+stationNumber);
        return service.searchPersonAndFamilyFromFireStation(stationNumber);
    }
}
