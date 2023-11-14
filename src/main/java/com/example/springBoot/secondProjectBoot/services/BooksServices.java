package com.example.springBoot.secondProjectBoot.services;

import com.example.springBoot.secondProjectBoot.model.Book;
import com.example.springBoot.secondProjectBoot.model.Person;
import com.example.springBoot.secondProjectBoot.repositories.BooksRepository;
import com.example.springBoot.secondProjectBoot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksServices {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksServices(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, Book updateBook, int id) {
        updateBook.setId_book(id);
        updateBook.setOwner(book.getOwner());
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void updateBookPersonId(Person person, int id) {
        Book book = findOne(id);
        book.setOwner(person);
        book.setTakenAt(new Date());
    }

    @Transactional
    public void returnBook(int id) {
        Book book = booksRepository.getOne(id);
        book.setTakenAt(null);
        book.setOwner(null);
    }

    public Optional<Person> findOwner(int id) {
        Optional<Person> optionalOwner = Optional.ofNullable(findOne(id).getOwner());
        return optionalOwner;
    }

    public List<Book> booksPagination(Integer page, Integer booksPerPage) {
        if (page != null && booksPerPage != null) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public List<Book> paginationAndOrderByDateOfWriting(Integer page, Integer booksPerPage) {
       return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("dateOfWriting"))).getContent();
    }

    public List<Book> orderByDateOfWriting() {
        return booksRepository.findAll(Sort.by("dateOfWriting"));
    }

    public List<Book> findByNameOfBookStartingWith(String startWith) {
        List<Book> books = booksRepository.findByNameOfBookStartingWith(firtsCharToUpperCase(startWith));
        if (books.isEmpty()) {
            books = Collections.EMPTY_LIST;
        }
        return books;
    }
    public String firtsCharToUpperCase(String startWith) {
        char[] str = startWith.toCharArray();
        char[] chars = {str[0]};
        String toUpp = String.valueOf(chars).toUpperCase();
        str[0] = toUpp.toCharArray()[0];
        return String.valueOf(str);
    }

    public boolean isTimeToReturnBook(Book book) {
        final long tenDaysInMillisec = 864_000_000L;
        Date currentTime = new Date();
        boolean isTimeToReturn =
                currentTime.getTime() - book.getTakenAt().getTime() >= tenDaysInMillisec ? false : true;
        return isTimeToReturn;
    }
}
