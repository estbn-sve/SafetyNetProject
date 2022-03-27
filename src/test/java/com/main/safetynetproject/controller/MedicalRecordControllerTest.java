package com.main.safetynetproject.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.service.MedicalRecordService;
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
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService service;

    @Test
    public void getAllMedicalRecordTest() throws Exception {
        when(service.getAllMedicalRecords()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/medicalrecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMedicalRecordTest_shouldReturnOK() throws Exception {
        when(service.getMedicalRecord(any())).thenReturn(new MedicalRecords());
        mockMvc.perform(get("/medicalrecord/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(service.getMedicalRecord(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/medicalrecord/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnOK() throws Exception {
        when(service.deleteMedicalRecord(any())).thenReturn(new MedicalRecords());
        mockMvc.perform(delete("/medicalrecord/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(service.deleteMedicalRecord(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalrecord/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void upDateMedicalRecordTest_shouldReturnOK() throws Exception {
        when(service.putMedicalRecord(any(),any())).thenReturn(new MedicalRecords());
        mockMvc.perform(put("/medicalrecord/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void upDateMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(service.putMedicalRecord(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/medicalrecord/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addMedicalRecord_shouldReturnOK()throws Exception {
        when(service.addMedicalRecord(any())).thenReturn(new MedicalRecords());
        mockMvc.perform(post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addMedicalRecord_shouldReturnKO()throws Exception {
        when(service.addMedicalRecord(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

}


