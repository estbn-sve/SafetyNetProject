package com.main.safetynetproject.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.main.safetynetproject.model.FireStations;
import com.main.safetynetproject.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService service;

    @Test
    public void getAllFireStationTest() throws Exception {
        when(service.getAllFireStations()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnOK() throws Exception {
        when(service.getFireStation(any())).thenReturn(new FireStations());
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnNotFound() throws Exception {
        when(service.getFireStation(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationTest_shouldReturnOK() throws Exception {
        when(service.deleteFireStation(any())).thenReturn(new FireStations());
        mockMvc.perform(delete("/firestation/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteFireStationTest_shouldReturnNotFound() throws Exception {
        when(service.deleteFireStation(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/firestation/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void upDateFireStationTest_shouldReturnOK() throws Exception {
        when(service.putFireStation(any(),any())).thenReturn(new FireStations());
        mockMvc.perform(put("/firestation/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void upDateFireStationTest_shouldReturnNotFound() throws Exception {
        when(service.putFireStation(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/firestation/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addFireStation_shouldReturnOK()throws Exception {
        when(service.addFireStation(any())).thenReturn(new FireStations());
        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addFireStation_shouldReturnKO()throws Exception {
        when(service.addFireStation(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

}
