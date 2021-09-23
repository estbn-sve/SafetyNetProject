package com.main.safetynetproject.object;

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

}

/*You are most likely missing a relational (like @OneToMany) annotation and/or @Entity annotation.

I had a same problem in:

@Entity
public class SomeFee {
    @Id
    private Long id;
    private List<AdditionalFee> additionalFees;
    //other fields, getters, setters..
}

class AdditionalFee {
    @Id
    private int id;
    //other fields, getters, setters..
}
additionalFees was the field causing the problem.

What I was missing and what helped me are the following:

@Entity annotation on the Generic Type argument (AdditionalFee) class;
@OneToMany (or any other type of appropriate relation fitting your case) annotation on the private List<AdditionalFee> additionalFees; field.
So, the working version looked like this:

@Entity
public class SomeFee {
    @Id
    private Long id;
    @OneToMany
    private List<AdditionalFee> additionalFees;
    //other fields, getters, setters..
}

@Entity
class AdditionalFee {
    @Id
    private int id;
    //other fields, getters, setters..
}*/