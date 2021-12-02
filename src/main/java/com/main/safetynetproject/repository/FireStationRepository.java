package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.FireStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FireStationRepository extends JpaRepository<FireStations, Integer> {
    Optional<FireStations> findByStation(int station);
    Optional<List<FireStations>> findAllByStationIn(List<Integer> stations);
    Optional<FireStations> findByAddress(String address);
    Optional<List<FireStations>> findAllByAddress(String address);
}
