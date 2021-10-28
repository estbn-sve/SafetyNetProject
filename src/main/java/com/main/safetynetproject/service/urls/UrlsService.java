package com.main.safetynetproject.service.urls;

import com.main.safetynetproject.controller.urls.dto.EnfantsInAddressWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonAndFamilyInFireStationWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonAndFireStationWithCountResponse;
import com.main.safetynetproject.controller.urls.dto.PersonInFireStationWithCountResponse;
import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.FireStationRepository;
import com.main.safetynetproject.repository.MedicalRecordRepository;
import com.main.safetynetproject.repository.PersonRepository;
import com.main.safetynetproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            //TODO afficher toutes les informations ?!
            result.famille = famille;
            return result;
        }).collect(Collectors.toList()));
        return e;
    }

    public List<String>searchNumberFromFireStationResponsible(final Integer stationNumber){
        FireStations fireStations = fsRepository.findByStation(stationNumber)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with station number " + stationNumber));
        List<Person> persons = fireStations.getAddress()
                .stream()
                .map(address -> pRepository.findAllByAddress(address))
                .flatMap(Collection::stream).collect(Collectors.toList());
        return persons.stream().map(Person::getPhone
        ).collect(Collectors.toList());
    }

    public PersonAndFireStationWithCountResponse searchPersonAndFireStationFromAddressResponsible(final String address){
        List<FireStations> fireStationsList = fsRepository.findAll();
        FireStations f = new FireStations();
        f.setStation(fireStationsList.stream().flatMapToInt(fireStations -> {
                    FireStations fs = new FireStations();
                    fs.builder().station(fireStations.getStation()).build();
                    return IntStream.of(fs.getStation());
                }).findFirst().getAsInt());
        /*FireStations finalFireStation = fireStationsList
                .stream()
                .forEach(fireStations -> fireStationsList
                        .stream()
                        .filter(fireStations1 -> fireStations1.getAddress()
                                .equals(address)
                        ).collect(Collectors.toList())
                );*/
        List<Person> personList = pRepository.findAllByAddress(address);
        PersonAndFireStationWithCountResponse p = new PersonAndFireStationWithCountResponse();
        p.setPersons(personList.stream().map(person -> {
            PersonAndFireStationWithCountResponse.Person result = new PersonAndFireStationWithCountResponse.Person();
            //TODO number station à 0 pas ok
            result.fireStation = f.getStation();
            result.firstName = person.getFirstName();
            result.lastName = person.getLastName();
            result.phone = person.getPhone();
            result.age = pService.countAge(person);
            result.medicalRecords = person.getMedicalRecords();
            return result;
        }).collect(Collectors.toList()));
        return p;
    }

    public PersonAndFamilyInFireStationWithCountResponse searchPersonAndFamilyFromFireStation(final Integer stationNumber){
        FireStations fireStations = fsRepository.findByStation(stationNumber)
                .orElseThrow(() -> new NoSuchElementException("There is no firestation with station number " + stationNumber));

        List<Person> persons = fireStations.getAddress()
                .stream()
                .map(address -> pRepository.findAllByAddress(address))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        PersonAndFamilyInFireStationWithCountResponse p = new PersonAndFamilyInFireStationWithCountResponse();
        p.setFoyers(persons.stream().map(person -> {
            PersonAndFamilyInFireStationWithCountResponse.Foyer foyer = new PersonAndFamilyInFireStationWithCountResponse.Foyer();
            PersonAndFamilyInFireStationWithCountResponse.member result = new PersonAndFamilyInFireStationWithCountResponse.member();
                result.firstName = person.getFirstName();
                result.lastName = person.getLastName();
                result.phone = person.getPhone();
                result.age = pService.countAge(person);
                result.medicalRecords = person.getMedicalRecords();
            foyer.members = Collections.singletonList(result);
            return foyer;
        }).collect(Collectors.toList()));
        return p;
    }
}
