package com.main.safetynetproject.controller;

import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private FireStationService service;

    @GetMapping("")
    public Iterable<FireStations> getFireStations(){
        log.info("GET /firestation");
        return service.getAllFireStations();
    }
    @GetMapping("/{id}")
    public ResponseEntity<FireStations> getFireStation(@PathVariable("id")final Integer id){
        log.info("GET /firestation"+id);
        try {
            return ResponseEntity.ok(service.getFireStation(id));
        } catch (NoSuchElementException e){
            log.error("GET /firestation/ Error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FireStations> deleteFireStation(@PathVariable("id")final Integer id){
        log.info("DELETE /firestation/"+id);
        try {
            return ResponseEntity.ok(service.deleteFireStation(id));
        }catch (NoSuchElementException e){
            log.error("DELETE /fireStation/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }    }

    @PostMapping("")
    public ResponseEntity<FireStations> AddFireStation(@RequestBody FireStations fireStations){
        log.info("POST /firestation/");
        try {
            return ResponseEntity.ok(service.addFireStation(fireStations));
        } catch (NoSuchElementException e){
            log.error("POST /firestation/" +" Error : "+e.getMessage());
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FireStations> updateFireStation (@PathVariable("id")final Integer id, @RequestBody FireStations fireStations){
        log.info("PUT /firestation/"+id);
        try {
            return ResponseEntity.ok(service.putFireStation(fireStations, id));
        } catch (NoSuchElementException e){
            log.error("PUT /firestation/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
