package com.main.safetynetproject.controller.urls.dto;

import com.main.safetynetproject.model.MedicalRecords;
import lombok.Data;

import java.util.List;

@Data
public class PersonAndFireStationWithCountResponse {
    public static class Person {
        public String firstName;
        public String lastName ;
        public String phone;
        public int age;
        public List<MedicalRecords> medicalRecords;
    }
    List<Integer> fireStation;
    List<Person> persons;
}
