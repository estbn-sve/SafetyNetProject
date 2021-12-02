package com.main.safetynetproject.repository;

import com.main.safetynetproject.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional <List<Person>> findAllByAddress(String address);
    Optional <List<Person>> findAllByFirstNameAndLastName(String firstName, String lastName);
    Optional <List<Person>> findByCity(String city);
}
