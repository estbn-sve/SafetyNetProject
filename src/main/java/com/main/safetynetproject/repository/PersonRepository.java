package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByAddress(String address);
    List<Person> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Person> findByCity(String city);
}
