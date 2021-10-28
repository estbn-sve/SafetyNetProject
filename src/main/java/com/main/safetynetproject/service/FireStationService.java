package com.main.safetynetproject.service;

import com.main.safetynetproject.repository.FireStationRepository;
import com.main.safetynetproject.model.FireStations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FireStationService {
    @Autowired
    private FireStationRepository repository;

    public FireStations getFireStation(final Integer id){
        return repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error with getFireStation "+id));
    }

    public Iterable<FireStations> getAllFireStations(){
        return repository.findAll();
    }

    public FireStations deleteFireStation(final Integer id){
        FireStations fs = repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error with deleteFireStation "+id));
        FireStations copy = FireStations.builder()
                .id(fs.getId())
                .address(fs.getAddress())
                .station(fs.getStation())
                .build();
        repository.delete(fs);
        return copy;
    }

    public FireStations addFireStation(FireStations fireStations){
        Integer id = fireStations.getId();
        if(!repository.existsById(id)){
            return repository.save(fireStations);
        } else  {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with addPerson "+id));
        }    }

    public FireStations putFireStation(FireStations currentFireStation,final Integer id){
        if (repository.existsById(id)){
            FireStations fireStations = currentFireStation;
            currentFireStation = repository.findById(id).get();
            Integer station = fireStations.getStation();
            if (station  != null){
                currentFireStation.setStation(station);
            }
            List<String> address = fireStations.getAddress();
            if ( address != null){
                currentFireStation.setAddress(address);
            }
            return currentFireStation;
        } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with putFireStation "+id));
        }
    }

    public List<FireStations> addAllFireStations(List<FireStations> fireStationsList){
        return repository.saveAll(fireStationsList);
    }
}
