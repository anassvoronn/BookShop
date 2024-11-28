package org.nastya.controller;

import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Genre;
import org.nastya.service.BookService;
import org.nastya.service.UserClient.UserClient;
import org.nastya.service.UserClient.UserContext;
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
    private final UserClient userClient;
    private final UserContext userContext;

    public BookController(BookService bookService, UserClient userClient, UserContext userContext) {
        this.bookService = bookService;
        this.userClient = userClient;
        this.userContext = userContext;
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
    public ResponseEntity<String> deleteBook(@PathVariable int id, @RequestHeader("sessionId") String sessionId) {
        log.info("Deleting book by id '{}'", id);
        if (!userClient.isUserAuthorized(userContext.getCurrentUserSession())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to perform this action");
        }
        try {
            bookService.deleteBook(id);
            log.info("Book '{}' deleted successfully", id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@RequestBody BookFormDTO bookFormDTO, @RequestHeader("sessionId") String sessionId) {
        log.info("Updating book with id '{}'", bookFormDTO.getId());
        if (!userClient.isUserAuthorized(userContext.getCurrentUserSession())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to perform this action");
        }
        try {
            bookService.updateBook(bookFormDTO);
            log.info("Book '{}' updated successfully", bookFormDTO.getId());
            return ResponseEntity.ok("Book updated successfully");
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", bookFormDTO.getId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookFormDTO bookFormDTO, @RequestHeader("sessionId") String sessionId) {
        log.info("Adding a new book");
        if (!userClient.isUserAuthorized(userContext.getCurrentUserSession())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to perform this action");
        }
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
