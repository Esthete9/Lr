package com.example.springBoot.secondProjectBoot.services;

import com.example.springBoot.secondProjectBoot.model.Book;
import com.example.springBoot.secondProjectBoot.model.Person;
import com.example.springBoot.secondProjectBoot.repositories.BooksRepository;
import com.example.springBoot.secondProjectBoot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleServices {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleServices(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByUsername(email).stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person updatePerson, int id) {
        updatePerson.setId_person(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksWithRelations(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(findOne(id).getBookList());
            final long tenDaysInMillisec = 864_000_000L;
            findOne(id).getBookList().forEach(book -> {
                if (new Date().getTime() - book.getTakenAt().getTime() >= tenDaysInMillisec) {
                    book.setExpired(true);
                }
            });
            return findOne(id).getBookList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }


//    public List<Book> findPersonsBooks(int id) {
//        return booksRepository.findAllById(Collections.singleton(id));
//    }

}
