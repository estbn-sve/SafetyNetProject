package com.main.safetynetproject.service;

import com.main.safetynetproject.dbb.repository.MedicalRecordsRepository;
import com.main.safetynetproject.object.MedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRecordsService {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;
    public List<MedicalRecords> addMedicalRecords(List<MedicalRecords> medicalRecordsList){
        return medicalRecordsRepository.saveAll(medicalRecordsList);
    }
}
