package com.main.safetynetproject.controller.urls;

import com.main.safetynetproject.controller.urls.dto.*;
import com.main.safetynetproject.service.urls.UrlsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlsService service;

    @Test
    public void searchPersonFromFireStationResponsible_Test_ShouldReturnOk() throws Exception {
        when(service.searchPersonFromFireStationResponsible(any())).thenReturn(new PersonInFireStationWithCountResponse());
        mockMvc.perform(get("/firestations?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchPersonFromFireStationResponsible_Test_ShouldReturnNotFound() throws Exception {
        when(service.searchPersonFromFireStationResponsible(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/firestations?stationNumber=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchEnfantFromAddressResponsible_Test_ShouldReturnOK() throws Exception {
        when(service.searchEnfantFromAddressResponsible(any())).thenReturn(new EnfantsInAddressWithCountResponse());
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchEnfantFromAddressResponsible_Test_ShouldReturnNotFound() throws Exception{
        when(service.searchEnfantFromAddressResponsible(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchNumberFromFireStationResponsible_Test_ShouldReturnOK() throws Exception{
        when(service.searchNumberFromFireStationResponsible(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/phoneAlert?firestation_Number=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchNumberFromFireStationResponsible_Test_ShouldReturnNotFound() throws Exception{
        when(service.searchNumberFromFireStationResponsible(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/phoneAlert?firestation_Number=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchPersonAndFireStationFromAddressResponsible_Test_ShouldReturnOK() throws Exception{
        when(service.searchPersonAndFireStationFromAddressResponsible(any())).thenReturn(new PersonAndFireStationWithCountResponse());
        mockMvc.perform(get("/fire?address=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchPersonAndFireStationFromAddressResponsible_Test_ShouldReturnNotFound() throws Exception{
        when(service.searchPersonAndFireStationFromAddressResponsible(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire?address=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchPersonAndFamilyFromFireStation_Test_ShouldReturnOK() throws Exception{
        List<PersonAndFamilyInFireStationWithCountResponse> pf = new ArrayList<>();
        when(service.searchPersonAndFamilyFromFireStation(any())).thenReturn(pf);
        mockMvc.perform(get("/flood/stations?stationNumber=4,3"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchPersonAndFamilyFromFireStation_Test_ShouldReturnNotFound() throws Exception{
        List<PersonAndFamilyInFireStationWithCountResponse> pf = new ArrayList<>();
        when(service.searchPersonAndFamilyFromFireStation(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/flood/stations?stationNumber=4,3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchPersonInfoByFirstNameAndLastName_Test_ShouldReturnOK() throws Exception{
        when(service.searchPersonInfoByFirstNameAndLastName(any(), any())).thenReturn(new PersonInfoByFirstNameAndLastNameResponse());
        mockMvc.perform(get("/personInfo?firstName=firstName&lastName=lastName"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchPersonInfoByFirstNameAndLastName_Test_ShouldReturnNotFound() throws Exception{
        when(service.searchPersonInfoByFirstNameAndLastName(any(), any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/personInfo?firstName=firstName&lastName=lastName"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchEmailByCity_Test_ShouldReturnOK() throws Exception{
        when(service.searchEmailByCity(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/communityEmail?city=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchEmailByCity_Test_ShouldReturnNotFound() throws Exception{
        when(service.searchEmailByCity(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/communityEmail?city=1"))
                .andExpect(status().isNotFound());
    }

}


