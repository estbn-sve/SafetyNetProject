package com.main.safetynetproject.dbb.repository;

import com.main.safetynetproject.object.FireStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationsRepository extends JpaRepository<FireStations, Integer> {
}
