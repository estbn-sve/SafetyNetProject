package com.main.safetynetproject.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.main.safetynetproject.model.Person;
import com.main.safetynetproject.service.PersonService;
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
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Test
    public void getAllPersonTest() throws Exception {
        when(service.getAllPerson()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonTest_shouldReturnOK() throws Exception {
        when(service.getPerson(any())).thenReturn(new Person());
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonTest_shouldReturnNotFound() throws Exception {
        when(service.getPerson(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonTest_shouldReturnOK() throws Exception {
        when(service.deletePerson(any())).thenReturn(new Person());
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void deletePersonTest_shouldReturnNotFound() throws Exception {
        when(service.deletePerson(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void upDatePersonTest_shouldReturnOK() throws Exception {
        when(service.putPerson(any(),any())).thenReturn(new Person());
        mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void upDatePersonTest_shouldReturnNotFound() throws Exception {
        when(service.putPerson(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

   @Test
    public void addPerson_shouldReturnOK()throws Exception {
        when(service.addPerson(any())).thenReturn(new Person());
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addPerson_shouldReturnKO()throws Exception {
        when(service.addPerson(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

}
