package com.main.safetynetproject.controller.urls;

import com.main.safetynetproject.controller.urls.dto.*;
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
    public List <PersonAndFamilyInFireStationWithCountResponse> searchPersonAndFamilyFromFireStation(@RequestParam("stationNumber")List<Integer> stationNumber){
        log.info("GET /flood/stations?stations="+stationNumber);
        return service.searchPersonAndFamilyFromFireStation(stationNumber);
    }

    @GetMapping("/personInfo")
    public PersonInfoByFirstNameAndLastNameResponse searchPersonInfoByFirstNameAndLastName(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName){
        log.info("GET /personInfo?firstName="+firstName+"&lastName"+lastName);
        return service.searchPersonInfoByFirstNameAndLastName(firstName,lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> searchEmailByCity(@RequestParam("city")String city){
        log.info(("GET /comunityEmail?city"+city));
        return service.searchEmailByCity(city);
    }
}
