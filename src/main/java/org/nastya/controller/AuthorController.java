package org.nastya.controller;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.service.AuthorService;
import org.nastya.service.exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthorFormDTO> getById(@PathVariable int id) {
        log.info("Getting a author by its id '{}'", id);
        try {
            return ResponseEntity.ok(authorService.findById(id));
        } catch (AuthorNotFoundException e) {
            log.error("No author found with ID: {}", id, e);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
        log.info("Deleting author by id '{}'", id);
        try {
            authorService.deleteAuthor(id);
        } catch (AuthorNotFoundException e) {
            log.error("No author found with ID: {}", id, e);
            return ResponseEntity.ok("Author not found");
        }
        log.info("Author '{}' deleted successfully", id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
