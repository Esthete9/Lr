package com.example.springBoot.secondProjectBoot.controller;


import com.example.springBoot.secondProjectBoot.dao.PersonDAO;
import com.example.springBoot.secondProjectBoot.model.Person;
import com.example.springBoot.secondProjectBoot.services.PeopleServices;
import com.example.springBoot.secondProjectBoot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PeopleServices peopleServices;
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PeopleServices peopleServices, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.peopleServices = peopleServices;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public List<Person> readOfPeople(Model model) {
        return peopleServices.findAll();
    }

    @GetMapping("/{id}")
    public String readOfOnePeople(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleServices.findOne(id));
        model.addAttribute("books", peopleServices.getBooksWithRelations(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String createPeople(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String updatePeopleGet(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleServices.findOne(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePeoplePatch(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                                    @PathVariable("id") int id, Model model) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleServices.update(person, id);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleServices.delete(id);
        return "redirect:/people";
    }
}
