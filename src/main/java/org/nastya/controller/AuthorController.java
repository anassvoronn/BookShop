package org.nastya.controller;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.service.AuthorService;
import org.nastya.service.exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

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

    @PutMapping
    public ResponseEntity<String> updateAuthor(@RequestBody AuthorFormDTO authorFormDTO) {
        log.info("Updating author with id '{}'", authorFormDTO.getId());
        try {
            authorService.updateAuthor(authorFormDTO);
            log.info("Author '{}' updated successfully", authorFormDTO.getId());
        } catch (AuthorNotFoundException e) {
            log.error("No author found with ID: {}", authorFormDTO.getId(), e);
            return ResponseEntity.ok("Author not found");
        }
        log.info("Author '{}' updated", authorFormDTO.getId());
        return ResponseEntity.ok("Author updated");
    }

    @PostMapping
    public ResponseEntity<String> addAuthor(@RequestBody AuthorFormDTO authorFormDTO) {
        log.info("Adding a new author");
        try {
            authorService.addAuthor(authorFormDTO);
            log.info("Author '{}' added successfully", authorFormDTO.getId());
            return ResponseEntity.ok("Author added successfully");
        } catch (Exception e) {
            log.error("Error occurred while adding the author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding the author");
        }
    }
}
