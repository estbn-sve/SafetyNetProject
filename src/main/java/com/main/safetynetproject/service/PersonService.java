package com.main.safetynetproject.service;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.repository.PersonRepository;
import com.main.safetynetproject.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person getPerson(final Integer id){
        return repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error with getPerson "+id));
    }

    public Iterable<Person> getAllPerson(){
        return repository.findAll();
    }

    public Person deletePerson(final Integer id){
        Person p = null;
        if (repository.existsById(id)){
            repository.deleteById(id);
            return p;
        } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with deletePerson "+id));
        }
    }

    public Person putPerson(Person currentPerson ,final Integer id){
        if (repository.existsById(id)){
            Person person = currentPerson;
            currentPerson = repository.findById(id).get();
            String firstName = person.getFirstName();
            if (firstName != null) {
                currentPerson.setFirstName(firstName);
            }
            String lastName = person.getLastName();
            if (lastName !=null){
                currentPerson.setLastName(lastName);
            }
            String address = person.getAddress();
            if (address !=null){
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null){
                currentPerson.setCity(city);
            }
            String zip = person.getZip();
            if(zip != null){
                currentPerson.setZip(zip);
            }
            String phone = person.getPhone();
            if(phone != null){
                currentPerson.setPhone(phone);
            }
            String email = person.getEmail();
            if(email != null){
                currentPerson.setEmail(email);
            }
            String birthDate = person.getBirthDate();
            if(birthDate != null){
                currentPerson.setBirthDate(birthDate);
            }
            List<MedicalRecords> medicalRecords = person.getMedicalRecords();
            if(medicalRecords != null){
                currentPerson.setMedicalRecords(medicalRecords);
            }
            return currentPerson;
        } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with putPerson "+id));
        }
    }

    public List<Person> addAllPersons(List<Person> personList){
        return repository.saveAll(personList);
    }

    public Person addPerson(Person person){
        Integer id = person.getId();
        if(!repository.existsById(id)){
            return repository.save(person);
        } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with addPerson "+id));
        }
    }
}
