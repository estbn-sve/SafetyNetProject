package com.main.safetynetproject.service;

import com.main.safetynetproject.dbb.repository.FireStationsRepository;
import com.main.safetynetproject.object.FireStations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FireStationsService {
    @Autowired
    private FireStationsRepository fireStationsRepository;
    public List<FireStations> addFireStations(List<FireStations> fireStationsList){
        return fireStationsRepository.saveAll(fireStationsList);
    }
}
