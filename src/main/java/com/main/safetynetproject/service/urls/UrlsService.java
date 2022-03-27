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
                .map(address -> pRepository.findAllByAddress(address)
                        .orElseThrow(() -> new NoSuchElementException("There is no persons with this address : " + address)))
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
        List<Person> personList = pRepository.findAllByAddress(address)
                .orElseThrow(() -> new NoSuchElementException("There is no persons with this address : " + address));
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
                .map(address -> pRepository.findAllByAddress(address)
                        .orElseThrow(() -> new NoSuchElementException("There is no persons with this address : " + address)))
                .flatMap(Collection::stream).map(Person::getPhone
                ).collect(Collectors.toList());
    }

    public PersonAndFireStationWithCountResponse searchPersonAndFireStationFromAddressResponsible(final String address){
        PersonAndFireStationWithCountResponse p = new PersonAndFireStationWithCountResponse();
        List<FireStations> fsList = fsRepository.findAllByAddress(address)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with this address " + address));
        p.setFireStation(fsList.stream().map(FireStations::getStation).collect(Collectors.toList()));
        List<Person> personList = pRepository.findAllByAddress(address)
                .orElseThrow(() -> new NoSuchElementException("There is no persons with this address " + address));
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

    public List<PersonAndFamilyInFireStationWithCountResponse> searchPersonAndFamilyFromFireStation(List<Integer> stationNumber) {
        List<FireStations> fireStationsList = fsRepository.findAllByStationIn(stationNumber)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with station number " + stationNumber));

        List<PersonAndFamilyInFireStationWithCountResponse>list = fireStationsList.stream()
                .map(FireStations::getAddress).flatMap(Collection::stream).distinct()
                .map(address-> {
                    PersonAndFamilyInFireStationWithCountResponse p1 = new PersonAndFamilyInFireStationWithCountResponse();
                    p1.setAddress(address);
                    List<PersonAndFamilyInFireStationWithCountResponse.Member> memberList = pRepository.findAllByAddress(address)
                            .orElseThrow(() -> new NoSuchElementException("There is no person with this address : " + address))
                            .stream().distinct()
                            .map(person -> {
                                PersonAndFamilyInFireStationWithCountResponse.Member member = new PersonAndFamilyInFireStationWithCountResponse.Member();
                                member.firstName = person.getFirstName();
                                member.lastName = person.getLastName();
                                member.phone = person.getPhone();
                                member.age = pService.countAge(person);
                                member.medicalRecords = person.getMedicalRecords();
                                return member;
                            }).collect(Collectors.toList());
                    p1.setFoyers(memberList);
                    return p1;
                }).collect(Collectors.toList());
        return list;
    }

    public PersonInfoByFirstNameAndLastNameResponse searchPersonInfoByFirstNameAndLastName(String firstName, String lastName){
        List<Person> personList = pRepository.findAllByFirstNameAndLastName(firstName,lastName)
                .orElseThrow(() -> new NoSuchElementException("There is no persons with this firstName : "+firstName +" and lastName : " + lastName));
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
        List<Person> personList = pRepository.findByCity(city)
                .orElseThrow(() -> new NoSuchElementException("There is no persons with this city " + city));
        List<String>result = new ArrayList<>();
        for (Person person : personList){
            result.add(person.getEmail());
        }
        result.stream().distinct().map(String::toString).collect(Collectors.toList());
        return result;
    }
}
