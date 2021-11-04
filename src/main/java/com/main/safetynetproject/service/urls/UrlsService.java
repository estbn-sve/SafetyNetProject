package com.main.safetynetproject.service.urls;

import com.main.safetynetproject.controller.urls.dto.*;
import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.FireStationRepository;
import com.main.safetynetproject.repository.MedicalRecordRepository;
import com.main.safetynetproject.repository.PersonRepository;
import com.main.safetynetproject.service.FireStationService;
import com.main.safetynetproject.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UrlsService {
    @Autowired
    private PersonRepository pRepository;
    @Autowired
    private FireStationRepository fsRepository;
    @Autowired
    MedicalRecordRepository mrRepository;
    @Autowired
    PersonService pService;
    @Autowired
    FireStationService fsService;

    public PersonInFireStationWithCountResponse searchPersonFromFireStationResponsible(final Integer stationNumber){
        FireStations fireStations = fsRepository.findByStation(stationNumber)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with station number " + stationNumber));

        List<Person> persons = fireStations.getAddress()
                .stream()
                .map(address -> pRepository.findAllByAddress(address))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        PersonInFireStationWithCountResponse p = new PersonInFireStationWithCountResponse();
        p.setPersons(persons.stream().map(person -> {
            PersonInFireStationWithCountResponse.Person result = new PersonInFireStationWithCountResponse.Person();
            result.firstName = person.getFirstName();
            result.lastName = person.getLastName();
            result.phone = person.getPhone();
            result.address = person.getAddress();
            return result;
        }).collect(Collectors.toList()));

        //TODO count
        int adultesNumber = 0;
        int enfantsNumber = 0;
        for (Person person : persons){
            if (pService.isAdult(person)) {
                adultesNumber = adultesNumber+1;
            } else {
                enfantsNumber = enfantsNumber+1;
            }
        }

        p.setAdultes(adultesNumber);
        p.setEnfants(enfantsNumber);
        return p;
    }

    public EnfantsInAddressWithCountResponse searchEnfantFromAddressResponsible(final String address){
        List<Person> personList = pRepository.findAllByAddress(address);
        List<Person> enfantList = personList
                .stream()
                .filter(person -> !pService.isAdult(person))
                .collect(Collectors.toList());

        EnfantsInAddressWithCountResponse e = new EnfantsInAddressWithCountResponse();
        e.setEnfants(enfantList.stream().map(person -> {
            EnfantsInAddressWithCountResponse.Enfant result = new EnfantsInAddressWithCountResponse.Enfant();
            result.firstName = person.getFirstName();
            result.lastName = person.getLastName();
            result.Age = pService.countAge(person);
            List<Person> famille = personList
                    .stream()
                    .filter(person1 -> !person1.getId()
                            .equals(person.getId()))
                    .collect(Collectors.toList());
            result.famille = famille;
            return result;
        }).collect(Collectors.toList()));
        return e;
    }

    public List<String>searchNumberFromFireStationResponsible(final Integer stationNumber){
        FireStations fireStations = fsRepository.findByStation(stationNumber)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with station number " + stationNumber));
        return fireStations.getAddress()
                .stream()
                .map(address -> pRepository.findAllByAddress(address))
                .flatMap(Collection::stream).map(Person::getPhone
                ).collect(Collectors.toList());
    }

    public PersonAndFireStationWithCountResponse searchPersonAndFireStationFromAddressResponsible(final String address){
        PersonAndFireStationWithCountResponse p = new PersonAndFireStationWithCountResponse();
        p.setFireStation(fsService.FireStationsByAddress(address));
        List<Person> personList = pRepository.findAllByAddress(address);
        p.setPersons(personList.stream().map(person -> {
            PersonAndFireStationWithCountResponse.Person result = new PersonAndFireStationWithCountResponse.Person();
            result.firstName = person.getFirstName();
            result.lastName = person.getLastName();
            result.phone = person.getPhone();
            result.age = pService.countAge(person);
            result.medicalRecords = person.getMedicalRecords();
            return result;
        }).collect(Collectors.toList()));
        return p;
    }

    //TODO crée un foyer par address
    public List<PersonAndFamilyInFireStationWithCountResponse> searchPersonAndFamilyFromFireStation(List<Integer> stationNumber) {
        List<FireStations> fireStationsList = fsRepository.findAllByStationIn(stationNumber);
        List<PersonAndFamilyInFireStationWithCountResponse> pList = new ArrayList<>();
        PersonAndFamilyInFireStationWithCountResponse p = new PersonAndFamilyInFireStationWithCountResponse();
        int i1 =0;
        int i2=0;
        for (FireStations fireStations : fireStationsList) {
            i1=i1+1;
            log.info("tours i1:"+i1);
            log.info("1er for StationNumber : "+fireStations.getStation()+" -----------------------");
            List<String> addressList = fireStations.getAddress();
            log.info("addressList : "+ addressList);
            for (String address : addressList) {
                i2 = i2+1;
                log.info("tours i2:"+i2);
                log.info("2eme for address : "+address+" -----------------------");
                List<Person> persons = pRepository.findAllByAddress(address);
                p.setAddress(address);
                p.setFoyers(persons.stream().map(person -> {
                    PersonAndFamilyInFireStationWithCountResponse.Member result = new PersonAndFamilyInFireStationWithCountResponse.Member();
                    result.firstName = person.getFirstName();
                    result.lastName = person.getLastName();
                    result.phone = person.getPhone();
                    result.age = pService.countAge(person);
                    result.medicalRecords = person.getMedicalRecords();
                    return result;
                }).collect(Collectors.toList()));
                pList.add(p);
            }
    }
        return pList;
}


    //TODO revoir enoncer "Si plusieurs personnes portent le même nom, elles doivent toutes apparaître."
    public PersonInfoByFirstNameAndLastNameResponse searchPersonInfoByFirstNameAndLastName(String firstName, String lastName){
        List<Person> personList = pRepository.findAllByFirstNameAndLastName(firstName,lastName);
        PersonInfoByFirstNameAndLastNameResponse p = new PersonInfoByFirstNameAndLastNameResponse();

        p.setPersons(personList.stream().map(person -> {
            PersonInfoByFirstNameAndLastNameResponse.Person result = new PersonInfoByFirstNameAndLastNameResponse.Person();
            result.firstName = person.getFirstName();
            result.lastName = person.getLastName();
            result.email = person.getEmail();
            result.age = pService.countAge(person);
            result.medicalRecords = person.getMedicalRecords();
            return result;
        }).collect(Collectors.toList()));
        return p;
    }

    public List<String> searchEmailByCity(String city){
        return pService.emailByCity(city);
    }
}
