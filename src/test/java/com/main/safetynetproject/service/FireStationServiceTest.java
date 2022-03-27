package com.main.safetynetproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.FireStationRepository;
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
public class FireStationServiceTest {

    @InjectMocks
    private FireStationService service;

    @Mock
    private FireStationRepository repository;

    @Test
    public void getFireStation_shouldReturnOK(){
        FireStations fs = new FireStations();
        when(repository.findById(any())).thenReturn(Optional.of(fs));
        assertEquals(service.getFireStation(1),fs);
    }

    @Test
    public void getFireStation_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getFireStation(1));
    }

    @Test
    public void getAllFireStations_shouldReturnOK(){
        List<FireStations> lfs = new ArrayList<>();
        when(repository.findAll()).thenReturn(lfs);
        assertEquals(service.getAllFireStations(),lfs);
    }

    @Test
    public void deleteFireStation_shouldReturnOK(){
        FireStations fs = new FireStations();
        fs.setStation(1);
        when(repository.findById(any())).thenReturn(Optional.of(fs));
        doNothing().when(repository).delete(any());
        FireStations fireStationResult = service.deleteFireStation(1);
        assertEquals(fs.getStation(), fireStationResult.getStation());
    }

    @Test
    public void deleteFireStation_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.deleteFireStation(1));
    }

    @Test
    public void putFireStation_shouldReturnOK(){
        FireStations fs = new FireStations();
        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(fs));
        assertEquals(service.putFireStation(fs,1),fs);
    }

    @Test
    public void putFireStation_shouldThrowNoSuchElement(){
        FireStations fs = new FireStations();
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> service.putFireStation(fs,1));
    }

    @Test
    public void addAllFireStations_shouldReturnOK(){
        List<FireStations> lfs = new ArrayList<>();
        when(repository.saveAll(any())).thenReturn(lfs);
        assertEquals(service.addAllFireStations(lfs),lfs);
    }

    @Test
    public void addFireStation_shouldReturnOK(){
        FireStations fs = new FireStations();
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(fs);
        assertEquals(service.addFireStation(fs),fs);
    }

    @Test
    public void addFireStation_shouldThrowNoSuchElement(){
        FireStations fs = new FireStations();
        when(repository.existsById(any())).thenReturn(true);
        assertThrows(NoSuchElementException.class, () -> service.addFireStation(fs));
    }

}

