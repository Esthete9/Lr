package com.example.springBoot.secondProjectBoot.dao;


import com.example.springBoot.secondProjectBoot.model.Book;
import com.example.springBoot.secondProjectBoot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> readOfAllPeople() {
//        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person readOfOnePeople(int id) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE id_person = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
//    }
//
//    public Optional<Person> readOfOnePeople(String email) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE email = ?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny();
//    }
//
//    public void createPeople(Person person) {
//        jdbcTemplate.update("INSERT INTO person(name, yearOfBirth, email) VALUES(?, ?, ?)",
//                person.getName(), person.getYearOfBirth(), person.getEmail());
//    }
//
//    public void updatePeople(Person person, int id) {
//        jdbcTemplate.update("UPDATE person SET name = ?, yearOfBirth = ?, email = ? WHERE id_person  = ?",
//                person.getName(), person.getYearOfBirth(), person.getEmail(), id);
//        System.out.println("I work");
//    }
//
//    public void deletePerson(int id) {
//        jdbcTemplate.update("DELETE FROM person WHERE id_person = ?", id);
//    }
//
//    public List<Book> findBooks(int id) {
//        return jdbcTemplate.query("SELECT * FROM book WHERE id_person = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
//    }

}