package com.main.safetynetproject.controller.urls;

import com.main.safetynetproject.service.urls.UrlsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class UrlsController {
    @Autowired
    private UrlsService service;

    @GetMapping("/firestations")
    public ResponseEntity<Object> searchPersonFromFireStationResponsible(@RequestParam("stationNumber")final Integer stationNumber){
        log.info("GET /firestations?stationNumber="+stationNumber);
        try {
            return ResponseEntity.ok(service.searchPersonFromFireStationResponsible(stationNumber));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<Object> searchEnfantFromAddressResponsible(@RequestParam("address")final String address){
        log.info("GET/ childAlert?address="+address);
        try {
            return ResponseEntity.ok(service.searchEnfantFromAddressResponsible(address));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<Object> searchNumberFromFireStationResponsible(@RequestParam("firestation_Number")final Integer stationNumber){
        log.info("GET /phoneAlert?firestation_Number="+stationNumber);
        try {
            return ResponseEntity.ok(service.searchNumberFromFireStationResponsible(stationNumber));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<Object> searchPersonAndFireStationFromAddressResponsible(@RequestParam("address")final String address){
        log.info("GET /fire?address="+address);
        try {
            return ResponseEntity.ok(service.searchPersonAndFireStationFromAddressResponsible(address));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Object> searchPersonAndFamilyFromFireStation(@RequestParam("stationNumber")List<Integer> stationNumber){
        log.info("GET /flood/stations?stationNumber="+stationNumber);
        try {
            return ResponseEntity.ok(service.searchPersonAndFamilyFromFireStation(stationNumber));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<Object> searchPersonInfoByFirstNameAndLastName(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName){
        log.info("GET /personInfo?firstName="+firstName+"&lastName="+lastName);
        try {
            return ResponseEntity.ok(service.searchPersonInfoByFirstNameAndLastName(firstName, lastName));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<Object> searchEmailByCity(@RequestParam("city")String city){
        log.info(("GET /communityEmail?city="+city));
        try {
            return ResponseEntity.ok(service.searchEmailByCity(city));
        }catch (NoSuchElementException e){
            log.error(""+"Error : "+e);
            return ResponseEntity.notFound().build();
        }
    }
}
