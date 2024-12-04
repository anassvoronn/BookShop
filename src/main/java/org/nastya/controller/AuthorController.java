package org.nastya.controller;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.service.AuthorService;
import org.nastya.service.exception.AuthorNotFoundException;
import org.nastya.service.exception.UserAuthorizationValidationException;
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
    private final  AuthorizationValidator authorizationValidator;


    public AuthorController(AuthorService authorService, AuthorizationValidator authorizationValidator) {
        this.authorService = authorService;
        this.authorizationValidator = authorizationValidator;
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
    public ResponseEntity<String> deleteAuthor(@PathVariable int id, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws AuthorNotFoundException, UserAuthorizationValidationException {
        log.info("Deleting author by id '{}'", id);
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            authorService.deleteAuthor(id);
            log.info("Author '{}' deleted successfully", id);
            return ResponseEntity.ok("Author deleted successfully");
        } catch (AuthorNotFoundException e) {
            log.error("No author found with ID: {}", id, e);
            throw e;
        }
    }

    @PutMapping
    public ResponseEntity<String> updateAuthor(@RequestBody AuthorFormDTO authorFormDTO, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws AuthorNotFoundException, UserAuthorizationValidationException {
        log.info("Updating author with id '{}'", authorFormDTO.getId());
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            authorService.updateAuthor(authorFormDTO);
            log.info("Author '{}' updated successfully", authorFormDTO.getId());
            return ResponseEntity.ok("Author updated successfully");
        } catch (AuthorNotFoundException e) {
            log.error("No author found with ID: {}", authorFormDTO.getId(), e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<String> addAuthor(@RequestBody AuthorFormDTO authorFormDTO, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        log.info("Adding a new author");
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            authorService.addAuthor(authorFormDTO);
            log.info("Author '{}' added successfully", authorFormDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Author added successfully");
        } catch (Exception e) {
            log.error("Error occurred while adding the author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding the author");
        }
    }
}
