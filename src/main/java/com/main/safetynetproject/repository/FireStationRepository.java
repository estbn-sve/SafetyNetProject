package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.FireStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FireStationRepository extends JpaRepository<FireStations, Integer> {
    Optional<FireStations> findByStation(int station);
}
