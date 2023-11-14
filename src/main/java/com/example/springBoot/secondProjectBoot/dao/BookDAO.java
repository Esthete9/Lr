package com.example.springBoot.secondProjectBoot.dao;

import com.example.springBoot.secondProjectBoot.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> readAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book readOneBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id_book = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
//    public void createBook(Book book) {
//        jdbcTemplate.update("INSERT INTO book(id_person, nameOfbook, dateOfWriting, author) VALUES (?, ?, ?, ?)",
//                book.getId_person(), book.getNameOfBook(), book.getDateOfWriting(), book.getAuthor());
//    }

    public void updateBook(Book book, int id) {
        jdbcTemplate.update("UPDATE book SET nameOfbook = ?, dateOfWriting = ?, author = ? WHERE  id_book = ?",
                 book.getNameOfBook(), book.getDateOfWriting(), book.getAuthor(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id_book = ?", id);
    }

    public void updateBookIdPerson(int idPerson, int id) {
        jdbcTemplate.update("UPDATE book SET id_person = ? WHERE id_book = ?", idPerson, id);
    }

    public void updateBookIdPerson(int id) {
        jdbcTemplate.update("UPDATE book SET id_person = ? WHERE id_book = ?", null, id);
    }

}
