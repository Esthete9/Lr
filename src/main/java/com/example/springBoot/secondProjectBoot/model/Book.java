package com.example.springBoot.secondProjectBoot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book", schema = "public")
public class Book {
    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_book;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id_person")
    private Person owner;
    @Column(name = "name_of_book")
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 1, max = 200, message = "Название книги должно содержать от 1 до 200 символов")
    private String nameOfBook;
    @Column(name = "date_of_writing")
    private int dateOfWriting;
    @Column(name = "author")
    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 1, max = 100, message = "Имя автора должно содержать от 1 до 100 символов")
    private String author;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book() {}

    public Book(int id_book, Integer id_person,  String nameOfBook, String author, int dateOfWriting, Date takenAt) {
        this.id_book = id_book;
        this.nameOfBook = nameOfBook;
        this.author = author;
        this.dateOfWriting = dateOfWriting;
        this.takenAt = takenAt;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDateOfWriting() {
        return dateOfWriting;
    }

    public void setDateOfWriting(int dateOfWriting) {
        this.dateOfWriting = dateOfWriting;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public boolean isHasOwner() {
        boolean bool = owner == null ? false : true;
        return bool;
    }
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id_book=" + id_book +
                ", nameOfBook='" + nameOfBook + '\'' +
                ", dateOfWriting=" + dateOfWriting +
                ", author='" + author + '\'' +
                '}';
    }
}

