package com.main.safetynetproject.dbb.repository;

import com.main.safetynetproject.object.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Integer> {
}
