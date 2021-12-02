package com.main.safetynetproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.PersonRepository;
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
public class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @Test
    public void getPerson_shouldReturnOK(){
        Person p = new Person();
        when(repository.findById(any())).thenReturn(Optional.of(p));
        assertEquals(service.getPerson(1),p);
    }

    @Test
    public void getPerson_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getPerson(1));
    }

    @Test
    public void getAllPerson_shouldReturnOK(){
        List<Person> lp = new ArrayList<>();
        when(repository.findAll()).thenReturn(lp);
        assertEquals(service.getAllPerson(),lp);
    }

    @Test
    public void deletePerson_shouldReturnOK(){
        Person p = new Person();
        p.setFirstName("david");
        when(repository.findById(any())).thenReturn(Optional.of(p));
        doNothing().when(repository).delete(any());
        Person personResult = service.deletePerson(1);
        assertEquals(p.getFirstName(), personResult.getFirstName());
    }

    @Test
    public void deletePerson_shouldThrowNoSuchElement(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.deletePerson(1));
    }

    @Test
    public void putPerson_shouldReturnOK(){
        Person p = new Person();
        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(p));
        assertEquals(service.putPerson(p,1),p);
    }

    @Test
    public void putPerson_shouldThrowNoSuchElement(){
        Person p = new Person();
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> service.putPerson(p,1));
    }

    @Test
    public void addAllPersons_shouldReturnOK(){
        List<Person> lp = new ArrayList<>();
        when(repository.saveAll(any())).thenReturn(lp);
        assertEquals(service.addAllPersons(lp),lp);
    }

    @Test
    public void addPerson_shouldReturnOK(){
        Person p = new Person();
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(p);
        assertEquals(service.addPerson(p),p);
    }

    @Test
    public void addPerson_shouldThrowNoSuchElement(){
        Person p = new Person();
        when(repository.existsById(any())).thenReturn(true);
        assertThrows(NoSuchElementException.class, () -> service.addPerson(p));
    }

}
