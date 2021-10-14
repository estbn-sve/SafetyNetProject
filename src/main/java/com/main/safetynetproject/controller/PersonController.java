package com.main.safetynetproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.service.PersonService;

@RestController
@Slf4j
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("")
    public Iterable<Person> getAllPerson() {
        log.info("GET /person");
        return service.getAllPerson();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") final Integer id){
        log.info("GET /person/"+id);
        try {
            return ResponseEntity.ok(service.getPerson(id));
        } catch (NoSuchElementException e){
            log.error("GET /person/ Error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable("id") final Integer id){
        log.info("DELETE /person/"+id);
        try {
            return ResponseEntity.ok(service.deletePerson(id));
        }catch (NoSuchElementException e){
            log.error("DELETE /person/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        log.info("POST /person/");
        try {
            return ResponseEntity.ok(service.addPerson(person));
        } catch (NoSuchElementException e){
            //TODO revoir code erreur et méthode
            log.error("POST /person/" +" Error : "+e.getMessage());
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") final Integer id, @RequestBody Person person){
        log.info("PUT /person/"+id);
        try {
            return ResponseEntity.ok(service.putPerson(person, id));
        } catch (NoSuchElementException e){
            log.error("PUT /person/"+id +" Error : "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
