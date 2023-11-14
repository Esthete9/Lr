package com.example.springBoot.secondProjectBoot.repositories;


import com.example.springBoot.secondProjectBoot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByUsername(String email);

}
