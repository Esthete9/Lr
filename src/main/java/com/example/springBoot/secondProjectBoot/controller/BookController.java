package com.example.springBoot.secondProjectBoot.controller;


import com.example.springBoot.secondProjectBoot.dao.BookDAO;
import com.example.springBoot.secondProjectBoot.dao.PersonDAO;
import com.example.springBoot.secondProjectBoot.model.Book;
import com.example.springBoot.secondProjectBoot.model.Person;
import com.example.springBoot.secondProjectBoot.services.BooksServices;
import com.example.springBoot.secondProjectBoot.services.PeopleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    private final BooksServices booksServices;
    private final PeopleServices peopleServices;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, BooksServices booksServices, PeopleServices peopleServices, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.booksServices = booksServices;
        this.peopleServices = peopleServices;
        this.personDAO = personDAO;
    }

    @GetMapping
    public List<Book> readAllBooks(Model model, @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                             @RequestParam(value = "sort_by_year", required = false) Boolean sort_by_year) {

        //model.addAttribute("library", booksServices.findAll());

        if (sort_by_year != null && sort_by_year) {
            model.addAttribute("library", booksServices.orderByDateOfWriting());
        }

        if (page != null && books_per_page != null && (sort_by_year == null || sort_by_year == false)) {
            model.addAttribute("library", booksServices.booksPagination(page, books_per_page));
        }

        if (page != null && books_per_page != null && sort_by_year != null && sort_by_year) {
            model.addAttribute("library", booksServices.paginationAndOrderByDateOfWriting(page, books_per_page));
        }

        return booksServices.findAll();
    }

    @GetMapping("/{id}")
    public String readOneBook(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksServices.findOne(id));

        Optional<Person> owner = booksServices.findOwner(id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleServices.findAll());
        }

        return "book/show";
    }

    @PatchMapping("/{id}/choosePerson")
    public String choosePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        booksServices.updateBookPersonId(person, id);
        return "redirect:/book/{id}";
    }

    @PatchMapping("/{id}/returnBook")
    public String returnBook(@PathVariable("id") int id) {
        booksServices.returnBook(id);
        return "redirect:/book/{id}";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        booksServices.create(book);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String updateBookGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksServices.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBookPatch(@ModelAttribute("book") @Valid Book updateBook, BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        Book book = booksServices.findOne(id);
        booksServices.update(book, updateBook, id);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksServices.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/search")
    public String searchBook() {
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearchBook(@RequestParam(name = "startWith", required = false) String startWith, Model model) {
        if (startWith != null) {
            model.addAttribute("books", booksServices.findByNameOfBookStartingWith(startWith));
        }
        return "book/search";
    }

}
