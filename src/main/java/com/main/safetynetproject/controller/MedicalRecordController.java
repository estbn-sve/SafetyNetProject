package com.main.safetynetproject.controller;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService service;

    @GetMapping("")
    public Iterable<MedicalRecords> getMedicalRecords(){
        log.info("GET /medicalrecord");
        return service.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecords> getMedicalRecord(@PathVariable("id")final Integer id){
        log.info("GET /medicalrecord/"+id);
        try {
            return ResponseEntity.ok(service.getMedicalRecord(id));
        } catch (NoSuchElementException e){
            log.error("GET /person/ Error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicalRecords> deleteMedicalRecord(@PathVariable("id")final Integer id){
        log.info("DELETE /medicalRecord/"+id);
        try {
            return ResponseEntity.ok(service.deleteMedicalRecord(id));
        }catch (NoSuchElementException e){
            log.error("DELETE /medicalRecord/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<MedicalRecords> addMedicalRecord(@RequestBody MedicalRecords medicalRecords){
        log.info("POST /medicalRecord/");
        try {
            return ResponseEntity.ok(service.addMedicalRecord(medicalRecords));
        } catch (NoSuchElementException e){
            //TODO revoir code erreur et méthode
            log.error("POST /medicalRecord/" +" Error : "+e.getMessage());
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(@PathVariable("id")final Integer id,@RequestBody MedicalRecords medicalRecords){
        log.info("PUT /medicalRecord/"+id);
        try {
            return ResponseEntity.ok(service.putMedicalRecord(medicalRecords, id));
        } catch (NoSuchElementException e){
            log.error("PUT /medicalRecord/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
