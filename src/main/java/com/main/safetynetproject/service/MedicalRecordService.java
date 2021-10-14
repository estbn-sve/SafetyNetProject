package com.main.safetynetproject.service;

import com.main.safetynetproject.repository.MedicalRecordRepository;
import com.main.safetynetproject.model.MedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository repository;

    public MedicalRecords getMedicalRecord(final Integer id){
        return repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error with getMedicalRecord "+id));
    }

    public Iterable<MedicalRecords> getAllMedicalRecords(){
        return repository.findAll();
    }

    public MedicalRecords deleteMedicalRecord(final Integer id){
        MedicalRecords mr = null;
        if (repository.existsById(id)){
            repository.deleteById(id);
            return mr;
        } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with deleteMedicalRecord "+id));
        }    }

    public MedicalRecords addMedicalRecord (MedicalRecords medicalRecords) {
        Integer id = medicalRecords.getId();
        if (!repository.existsById(id)) {
            return repository.save(medicalRecords);
        } else {
            return repository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Error with addPerson " + id));
        }
    }

    public MedicalRecords putMedicalRecord(MedicalRecords currentMedicalRecord, final Integer id) {
        if (repository.existsById(id)) {
            MedicalRecords medicalRecords = currentMedicalRecord;
            currentMedicalRecord = repository.findById(id).get();
            List<String> medications = medicalRecords.getMedications();
            if (medications != null) {
                currentMedicalRecord.setMedications(medications);
            }
            List<String> allergies = medicalRecords.getAllergies();
            if (allergies != null) {
                currentMedicalRecord.setAllergies(allergies);
            }
            return currentMedicalRecord;
            } else {
            return repository.findById(id).orElseThrow(()->
                    new NoSuchElementException("Error with putMedicalRecord "+id));
            }
        }

        public List<MedicalRecords> addAllMedicalRecords (List < MedicalRecords > medicalRecordsList) {
            return repository.saveAll(medicalRecordsList);
        }
}
