package org.nastya.controller;

import org.nastya.entity.Author;
import org.nastya.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllUsers() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable int id) {
        return authorService.findById(id);
    }
}
