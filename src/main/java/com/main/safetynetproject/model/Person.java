package com.main.safetynetproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;
    String address;
    String city;
    String zip;
    String phone;
    String email;
    String birthDate;
    @OneToMany(cascade = CascadeType.ALL)
    List<MedicalRecords> medicalRecords;

    /*@Builder
    public class CopyPerson {
        String firstName;
        String lastName;
        String address;
        String city;
        String zip;
        String phone;
        String email;
        String birthDate;
        List<MedicalRecords> medicalRecords;
    }*/
}