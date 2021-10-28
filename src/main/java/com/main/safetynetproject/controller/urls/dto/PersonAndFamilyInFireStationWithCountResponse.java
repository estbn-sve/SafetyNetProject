package com.main.safetynetproject.controller.urls.dto;

import com.main.safetynetproject.model.MedicalRecords;
import com.main.safetynetproject.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class PersonAndFamilyInFireStationWithCountResponse {
    public static class Foyer {
        public List<member> members;
    }
    public static class member{
        public String firstName;
        public String lastName;
        public String phone;
        public int age;
        public List<MedicalRecords> medicalRecords;
    }
    List<Foyer> Foyers;
}
