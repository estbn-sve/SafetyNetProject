package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.FireStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStations, Integer> {
}
