package com.main.safetynetproject.service;

import com.main.safetynetproject.dbb.repository.PersonRepository;
import com.main.safetynetproject.object.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> addPersons(List<Person> personList){
        return personRepository.saveAll(personList);
    }
}
