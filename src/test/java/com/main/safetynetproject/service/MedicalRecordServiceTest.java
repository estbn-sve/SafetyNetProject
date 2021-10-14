package com.main.safetynetproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService service;

    @MockBean
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
    public void deletePerson_shouldReturnOK(){
        MedicalRecords mr = new MedicalRecords();
        when(repository.findById(any())).thenReturn(Optional.of(mr));
        assertEquals(service.deleteMedicalRecord(1),mr);
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
        when(repository.save(any())).thenReturn(mr);
        assertThrows(NoSuchElementException.class, () -> service.addMedicalRecord(mr));
    }

}
