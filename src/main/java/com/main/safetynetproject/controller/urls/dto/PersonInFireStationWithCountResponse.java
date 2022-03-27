package com.main.safetynetproject.controller.urls.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonInFireStationWithCountResponse {
    public static class Person {
        public String firstName;
        public String lastName ;
        public String phone;
        public String address;
    }
    List<Person> persons;
    Integer adultes;
    Integer enfants;
}
