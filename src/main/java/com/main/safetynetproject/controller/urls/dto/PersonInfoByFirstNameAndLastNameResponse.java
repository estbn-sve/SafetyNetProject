package com.main.safetynetproject.controller.urls.dto;
import com.main.safetynetproject.model.MedicalRecords;
import lombok.Data;

import java.util.List;

@Data
public class PersonInfoByFirstNameAndLastNameResponse {
    public static class Person {
        public String firstName;
        public String lastName ;
        public int age;
        public String email;
        public List<MedicalRecords> medicalRecords;
    }
    List<Person> persons;
}
