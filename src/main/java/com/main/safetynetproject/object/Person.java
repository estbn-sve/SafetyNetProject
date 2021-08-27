package com.main.safetynetproject.object;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Person {
    String firstName;
    String lastName;
    String address;
    String city;
    String zip;
    String phone;
    String email;
    String birthDate;
    List<MedicalRecords> medicalRecords;
}
