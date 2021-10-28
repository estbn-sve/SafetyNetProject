package com.main.safetynetproject.controller.urls.dto;

import com.main.safetynetproject.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class EnfantsInAddressWithCountResponse {
    public static class Enfant {
            public String firstName;
            public String lastName ;
            public int Age;
            public List<Person> famille;

    }
    List<Enfant> enfants;
}
