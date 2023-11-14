package com.example.springBoot.secondProjectBoot.util;


import com.example.springBoot.secondProjectBoot.model.Person;
import com.example.springBoot.secondProjectBoot.services.PeopleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleServices peopleServices;

    @Autowired
    public PersonValidator( PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleServices.findByEmail(person.getUsername()).isPresent()) {
             errors.rejectValue("email", "", "This email is already taken");
        }
    }

//    public void validateUpdate(Object o, Errors errors, int id) {
//        Person person = (Person) o;
//        List<Person> personList = personDAO.readOfAllPeople();
//        if (personDAO.readOfOnePeople(person.getEmail()).isPresent() && !personDAO.readOfOnePeople(id).getEmail().equals(person.getEmail())) {
//            errors.rejectValue("email", "", "This email is already taken");
//        }
//    }
}
