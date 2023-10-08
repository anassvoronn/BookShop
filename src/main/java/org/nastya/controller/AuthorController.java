package org.nastya.controller;

import org.nastya.entity.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @GetMapping
    public List<Author> getAllUsers() {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor("Александр Грин", "1880-08-25", "Мужской", "Россия"));
        authors.add(createAuthor("Джейн Остин", "1775-12-16", "Женский", "Англия"));
        authors.add(createAuthor("Фёдор Достоевский", "1821-11-11", "Мужской", "Россия"));
        return authors;
    }

    @Deprecated
    private Author createAuthor(String name, String birthDate, String gender, String country) {
        Author author = new Author();
        author.setName(name);
        LocalDate date = LocalDate.parse(birthDate);
        author.setBirthDate(date);
        author.setGender(gender);
        author.setCountry(country);
        return author;
    }
}
