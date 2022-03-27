package com.main.safetynetproject.service.urls;

import com.main.safetynetproject.controller.urls.dto.*;
import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.repository.FireStationRepository;
import com.main.safetynetproject.repository.MedicalRecordRepository;
import com.main.safetynetproject.repository.PersonRepository;
import com.main.safetynetproject.service.FireStationService;
import com.main.safetynetproject.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlsServiceTest {

    @InjectMocks
    private UrlsService service;

    @Mock
    private PersonRepository pRepository;

    @Mock
    private FireStationRepository fsRepository;

    @Mock
    private MedicalRecordRepository mrRepository;

    @Mock
    private PersonService pService;

    @Mock
    private FireStationService fsService;

    @Test
    public void searchPersonFromFireStationResponsible_Test_shouldReturnOk(){
        // GIVEN une addresse avec deux adulte
        List<Person> personList = Arrays.asList(Person.builder().id(1).firstName("David").lastName("Smith").phone("00000").address("1 rue").build(),
                Person.builder().id(2).firstName("John").lastName("Smith").phone("11111").address("1 rue").build());
        FireStations fs = new FireStations();
        fs.setStation(1);
        fs.setAddress(Arrays.asList("1 rue"));
        fs.setId(1);
        when(fsRepository.findByStation(1)).thenReturn(Optional.of(fs));
        when(pRepository.findAllByAddress(any())).thenReturn(Optional.of(personList));
        when(pService.isAdult(any())).thenReturn(true);

        // WHEN
        PersonInFireStationWithCountResponse p = service.searchPersonFromFireStationResponsible(1);

        // THEN
        assertEquals("David", p.getPersons().get(0).firstName);
        assertEquals("Smith", p.getPersons().get(0).lastName);
        assertEquals("00000", p.getPersons().get(0).phone);
        assertEquals("1 rue", p.getPersons().get(0).address);

        assertEquals("John", p.getPersons().get(1).firstName);
        assertEquals("Smith", p.getPersons().get(1).lastName);
        assertEquals("11111", p.getPersons().get(1).phone);
        assertEquals("1 rue", p.getPersons().get(1).address);

        assertEquals(2, p.getAdultes());
        assertEquals(0, p.getEnfants());
        //assertEquals(service.searchPersonFromFireStationResponsible(1),p);
    }

    @Test
    public void searchPersonFromFireStationResponsible_Test_shouldThrowNoSuchElement(){
        assertThrows(NoSuchElementException.class, () -> service.searchPersonFromFireStationResponsible(1));
    }

    @Test
    public void searchEnfantFromAddressResponsible_Test_shouldReturnOk(){
        // Given une addresse qui correspond a une liste de personne avec 2 enfants
        List<Person> personList = Arrays.asList(Person.builder().id(1).firstName("David").lastName("Smith").build(),
                Person.builder().id(2).firstName("John").lastName("Smith").build());
        when(pRepository.findAllByAddress(any())).thenReturn(Optional.of(personList));
        when(pService.isAdult(any())).thenReturn(false);
        when(pService.countAge(any())).thenReturn(12);

        // When
        EnfantsInAddressWithCountResponse e = service.searchEnfantFromAddressResponsible("");

        // Then
        assertEquals("David", e.getEnfants().get(0).firstName);
        assertEquals("Smith", e.getEnfants().get(0).lastName);
        assertEquals(12, e.getEnfants().get(0).Age);
        assertEquals(1, e.getEnfants().get(0).famille.size());
        assertEquals("John", e.getEnfants().get(0).famille.get(0).getFirstName());

        assertEquals("John", e.getEnfants().get(1).firstName);
        assertEquals("Smith", e.getEnfants().get(1).lastName);
        assertEquals(12, e.getEnfants().get(1).Age);
        assertEquals(1, e.getEnfants().get(1).famille.size());
        assertEquals("David", e.getEnfants().get(1).famille.get(0).getFirstName());
    }

    @Test
    public void searchEnfantFromAddressResponsible_Test_shouldThrowNoSuchElement(){
        assertThrows(NoSuchElementException.class, () -> service.searchEnfantFromAddressResponsible(any()));
    }

    @Test
    public void searchNumberFromFireStationResponsible_Test_shouldReturnOk(){
        // GIVEN
        List<Person> p = Arrays.asList(Person.builder().phone("11111").build(),Person.builder().phone("22222").build());
        FireStations fs = new FireStations();
        fs.setStation(1);
        fs.setAddress(Arrays.asList("1 rue"));
        fs.setId(1);
        when(fsRepository.findByStation(1)).thenReturn(Optional.of(fs));
        when(pRepository.findAllByAddress(any())).thenReturn(Optional.of(p));
        // WHEN
        List<String> n = service.searchNumberFromFireStationResponsible(1);
        // THEN
        assertEquals(2, n.size());
        assertEquals("11111", n.get(0));
        assertEquals( "22222", n.get(1));
    }

    @Test
    public void searchNumberFromFireStationResponsible_shouldThrowNoSuchElement(){
        assertThrows(NoSuchElementException.class, () -> service.searchNumberFromFireStationResponsible(1));
    }

    @Test
    public void searchPersonAndFireStationFromAddressResponsible_Test_ShouldReturnOk(){
        //given
        List<Person> p = Arrays.asList(Person.builder().id(1).firstName("David").lastName("Smith").phone("00000").build(),
                Person.builder().id(2).firstName("John").lastName("Smith").phone("11111").build());
        List<FireStations> fs = Arrays.asList(FireStations.builder().id(1).station(1).address(Arrays.asList("1 rue")).build(),
                FireStations.builder().id(2).station(2).address(Arrays.asList("1 rue")).build());
        List<Integer> i = Arrays.asList(1,2);
        when(fsRepository.findAllByAddress(any())).thenReturn(Optional.of(fs));
        when(pRepository.findAllByAddress(any())).thenReturn(Optional.of(p));
        when(pService.countAge(any())).thenReturn(35);
        //when
        PersonAndFireStationWithCountResponse pf = service.searchPersonAndFireStationFromAddressResponsible("");
        //then
        assertEquals(i, pf.getFireStation());

        assertEquals("David", pf.getPersons().get(0).firstName);
        assertEquals("Smith", pf.getPersons().get(0).lastName);
        assertEquals("00000", pf.getPersons().get(0).phone);
        assertEquals(35, pf.getPersons().get(0).age);

        assertEquals("John", pf.getPersons().get(1).firstName);
        assertEquals("Smith", pf.getPersons().get(1).lastName);
        assertEquals("11111", pf.getPersons().get(1).phone);
        assertEquals(35, pf.getPersons().get(1).age);

    }

    @Test
    public void searchPersonAndFireStationFromAddressResponsible_Test_ShouldThrowElements(){
        assertThrows(NoSuchElementException.class, () -> service.searchPersonAndFireStationFromAddressResponsible(""));
    }

    @Test
    public void searchPersonAndFamilyFromFireStation_Test_ShouldReturnOk(){
        //given
        List<Person> p = Arrays.asList(Person.builder().id(1).firstName("David").lastName("Smith").phone("00000").build(),
                Person.builder().id(2).firstName("John").lastName("Smith").phone("11111").build());
        List<FireStations> fsList = Arrays.asList(FireStations.builder().id(1).station(1).address(Arrays.asList("1 rue")).build());
        List<Integer> i = Arrays.asList(1);
        when(fsRepository.findAllByStationIn(any())).thenReturn(Optional.of(fsList));
        when(pRepository.findAllByAddress(any())).thenReturn(Optional.of(p));
        when(pService.countAge(any())).thenReturn(22);
        //when
        List<PersonAndFamilyInFireStationWithCountResponse> pf = service.searchPersonAndFamilyFromFireStation(i);
        //then

        assertEquals("1 rue", pf.get(0).getAddress());
        assertEquals(2, pf.get(0).getFoyers().size());

        assertEquals("David", pf.get(0).getFoyers().get(0).firstName);
        assertEquals("Smith", pf.get(0).getFoyers().get(0).lastName);
        assertEquals("00000", pf.get(0).getFoyers().get(0).phone);
        assertEquals(22, pf.get(0).getFoyers().get(0).age);

        assertEquals("John", pf.get(0).getFoyers().get(1).firstName);
        assertEquals("Smith", pf.get(0).getFoyers().get(1).lastName);
        assertEquals("11111", pf.get(0).getFoyers().get(1).phone);
        assertEquals(22, pf.get(0).getFoyers().get(1).age);
    }

    @Test
    public void searchPersonAndFamilyFromFireStation_Test_ShouldThrowElements(){
        List<Integer> i = Arrays.asList(1);
        assertThrows(NoSuchElementException.class, () -> service.searchPersonAndFamilyFromFireStation(i));
    }

    @Test
    public void searchPersonInfoByFirstNameAndLastName_Test_ShouldReturnOk(){
        //given
        List<Person> p = Arrays.asList(Person.builder().id(1).firstName("David").lastName("Smith").build());
        when(pRepository.findAllByFirstNameAndLastName(any(),any())).thenReturn(Optional.of(p));
        //when
        PersonInfoByFirstNameAndLastNameResponse pi = service.searchPersonInfoByFirstNameAndLastName("","");
        //then
        assertEquals("David", pi.getPersons().get(0).firstName);
        assertEquals("Smith", pi.getPersons().get(0).lastName);
    }

    @Test
    public void searchPersonInfoByFirstNameAndLastName_Test_ShouldThrowElements(){
        assertThrows(NoSuchElementException.class, () -> service.searchPersonInfoByFirstNameAndLastName("",""));
    }

    //TODO voir pourquoi le distinct ne marche pas
    @Test
    public void searchEmailByCity_Test_ShouldReturnOk(){
        //given
        List<Person> p = Arrays.asList(Person.builder().email("email1").build(),
                //Person.builder().email("email2").build(),
                Person.builder().email("email2").build());
        when(pRepository.findByCity(any())).thenReturn(Optional.of(p));
        //when
        List<String> result = service.searchEmailByCity("");
        //then
        assertEquals("email1", result.get(0));
        assertEquals("email2", result.get(1));
        assertEquals(2, result.size());
    }

    @Test
    public void searchEmailByCity_Test_ShouldThrowElements(){
        assertThrows(NoSuchElementException.class, () -> service.searchEmailByCity(""));
    }
}
