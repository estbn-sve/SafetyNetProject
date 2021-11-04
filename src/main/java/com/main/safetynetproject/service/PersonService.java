package com.main.safetynetproject.service;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.repository.PersonRepository;
import com.main.safetynetproject.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        Person p = repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error with deletePerson "+id));
        Person copy = Person.builder()
                .id(p.getId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .address(p.getAddress())
                .city(p.getCity())
                .zip(p.getZip())
                .phone(p.getPhone())
                .email(p.getEmail())
                .birthDate(p.getBirthDate())
                .medicalRecords(p.getMedicalRecords() == null ? null : p.getMedicalRecords().stream().map(elem -> MedicalRecords.builder()
                        .allergies(elem.getAllergies().stream().map(allergie -> new String(allergie)).collect(Collectors.toList()))
                        .medications(elem.getMedications().stream().map(medication -> new String(medication)).collect(Collectors.toList()))
                        .id(elem.getId())
                        .build()).collect(Collectors.toList()))
                .build();
        repository.delete(p);
        return copy;
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

    public Boolean isAdult(Person person){
        LocalDate date = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(person.getBirthDate(),format);
        int i = Math.toIntExact(birthDate.until(date, ChronoUnit.YEARS));
        return i > 18;
    }

    public int countAge(Person person){
        LocalDate date = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(person.getBirthDate(),format);
        return Math.toIntExact(birthDate.until(date, ChronoUnit.YEARS));
    }

    public List<String> emailByCity(String city){
        List<Person> personList = repository.findByCity(city);
        List<String>result = new ArrayList<>();
        for (Person person : personList){
            result.add(person.getEmail());
        }
        return result;
    }
}
