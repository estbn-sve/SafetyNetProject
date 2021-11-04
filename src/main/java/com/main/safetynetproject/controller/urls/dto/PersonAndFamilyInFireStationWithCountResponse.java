package com.main.safetynetproject.controller.urls.dto;

import com.main.safetynetproject.model.MedicalRecords;
import lombok.Data;

import java.util.List;

@Data
public class PersonAndFamilyInFireStationWithCountResponse {
    /*public static class Foyer {
        public String address;
        public List<Member> members;
    }*/
    public static class Member {
        public String firstName;
        public String lastName;
        public String phone;
        public int age;
        public List<MedicalRecords> medicalRecords;
    }
    List<Member> foyers;
    public String address;
}
