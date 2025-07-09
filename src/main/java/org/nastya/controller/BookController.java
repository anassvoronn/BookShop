package org.nastya.controller;

import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Genre;
import org.nastya.lib.auth.AuthorizationValidator;
import org.nastya.lib.auth.exception.UserAuthorizationValidationException;
import org.nastya.service.BookService;
import org.nastya.service.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final AuthorizationValidator authorizationValidator;

    public BookController(BookService bookService, AuthorizationValidator authorizationValidator) {
        this.bookService = bookService;
        this.authorizationValidator = authorizationValidator;
    }

    @GetMapping
    public List<BookListItemDTO> getAllBooks() {
        log.info("Getting all books");
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookFormDTO> getById(@PathVariable int id) {
        log.info("Getting a book by its id '{}'", id);
        try {
            return ResponseEntity.ok(bookService.findById(id));
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", id, e);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws BookNotFoundException, UserAuthorizationValidationException {
        log.info("Deleting book by id '{}'", id);
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            bookService.deleteBook(id);
            log.info("Book '{}' deleted successfully", id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", id, e);
            throw e;
        }
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@RequestBody BookFormDTO bookFormDTO, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws BookNotFoundException, UserAuthorizationValidationException {
        log.info("Updating book with id '{}'", bookFormDTO.getId());
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            bookService.updateBook(bookFormDTO);
            log.info("Book '{}' updated successfully", bookFormDTO.getId());
            return ResponseEntity.ok("Book updated successfully");
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", bookFormDTO.getId(), e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookFormDTO bookFormDTO, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        log.info("Adding a new book");
        authorizationValidator.validateUserAuthorization(sessionId);
        try {
            bookService.addBook(bookFormDTO);
            log.info("Book '{}' added successfully", bookFormDTO.getId());
            return ResponseEntity.ok("Book added successfully");
        } catch (Exception e) {
            log.error("Error occurred while adding the book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding the book");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookListItemDTO>> searchBooks(@RequestParam(required = false) Genre genre,
                                                             @RequestParam(required = false) String title,
                                                             @RequestParam(required = false) String publishingYear,
                                                             @RequestParam(required = false) Integer authorId) {
        log.info("Searching books with genre '{}', title '{}', publishingYear '{}', and authorId '{}'",
                genre,
                title,
                publishingYear,
                authorId);
        List<BookListItemDTO> books = bookService.findByGenreAndByTitleAndByPublishingYearAndByAuthor(genre, title, publishingYear, authorId);
        return ResponseEntity.ok(books);
    }
}
