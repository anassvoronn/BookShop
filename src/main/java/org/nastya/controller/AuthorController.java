package org.nastya.controller;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorListItemDTO> getAllUsers() {
        log.info("Getting all authors");
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public AuthorFormDTO getById(@PathVariable int id) {
        log.info("Getting a author by its id '{}'", id);
        return authorService.findById(id);
    }
}
