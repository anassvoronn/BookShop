package org.nastya.controller;

import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.service.BookService;
import org.nastya.service.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

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
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        log.info("Deleting book by id '{}'", id);
        try {
            bookService.deleteBook(id);
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", id, e);
            return ResponseEntity.ok("Book not found");
        }
        log.info("Book '{}' deleted successfully", id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@RequestBody BookFormDTO bookFormDTO) {
        log.info("Updating book with id '{}'", bookFormDTO.getId());
        try {
            bookService.updateBook(bookFormDTO);
            log.info("Book '{}' updated successfully", bookFormDTO.getId());
        } catch (BookNotFoundException e) {
            log.error("No book found with ID: {}", bookFormDTO.getId(), e);
            return ResponseEntity.ok("book not found");
        }
        log.info("Book '{}' updated", bookFormDTO.getId());
        return ResponseEntity.ok("Book updated");
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookFormDTO bookFormDTO) {
        log.info("Adding a new book");
        try {
            bookService.addBook(bookFormDTO);
            log.info("Book '{}' added successfully", bookFormDTO.getId());
            return ResponseEntity.ok("Book added successfully");
        } catch (Exception e) {
            log.error("Error occurred while adding the book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding the book");
        }
    }
}
