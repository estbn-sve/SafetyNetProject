package com.main.safetynetproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService service;

    @Mock
    private MedicalRecordRepository repository;

    @Test
    public void getMedicalRecord_shouldReturnOK(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.findById(any())).thenReturn(Optional.of(mr));
        assertEquals(service.getMedicalRecord(1),mr);
    }

    @Test
    public void getMedicalRecord_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getMedicalRecord(1));
    }

    @Test
    public void getAllMedicalRecord_shouldReturnOK(){
        List<MedicalRecords> lmr = new ArrayList<>();
        when(repository.findAll()).thenReturn(lmr);
        assertEquals(service.getAllMedicalRecords(),lmr);
    }

    @Test
    public void deleteMedicalRecord_shouldReturnOK(){
        MedicalRecords mr = new MedicalRecords();
        mr.setId(1);
        when(repository.findById(any())).thenReturn(Optional.of(mr));
        doNothing().when(repository).delete(any());
        MedicalRecords medicalRecordResult = service.deleteMedicalRecord(1);
        assertEquals(mr.getMedications(), medicalRecordResult.getMedications());
    }

    @Test
    public void deleteMedicalRecord_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.deleteMedicalRecord(1));
    }

    @Test
    public void putMedicalRecord_shouldReturnOK(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(mr));
        assertEquals(service.putMedicalRecord(mr,1),mr);
    }

    @Test
    public void putMedicalRecord_shouldThrowNoSuchElement(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> service.putMedicalRecord(mr,1));
    }

    @Test
    public void addAllMedicalRecords_shouldReturnOK(){
        List<MedicalRecords> lmr = new ArrayList<>();
        when(repository.saveAll(any())).thenReturn(lmr);
        assertEquals(service.addAllMedicalRecords(lmr),lmr);
    }

    @Test
    public void addMedicalRecord_shouldReturnOK(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(mr);
        assertEquals(service.addMedicalRecord(mr),mr);
    }

    @Test
    public void addMedicalRecord_shouldThrowNoSuchElement(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.existsById(any())).thenReturn(true);
        assertThrows(NoSuchElementException.class, () -> service.addMedicalRecord(mr));
    }

}
