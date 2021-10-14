package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MedicalRecordRepository extends JpaRepository<MedicalRecords, Integer> {
}
