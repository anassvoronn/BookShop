package org.nastya.controller;

import org.nastya.service.BookService;
import org.nastya.service.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookViews")
public class BookViewsController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookViewsController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<String> incrementViewCount(@PathVariable int bookId) {
        log.info("Received request to increment view count for book with ID: {}", bookId);
        try {
            bookService.incrementViewCount(bookId);
            log.info("Successfully incremented view count for book with ID: {}", bookId);
            return ResponseEntity.ok("View count incremented successfully.");
        } catch (BookNotFoundException e) {
            log.warn("Book not found with ID: {}", bookId);
            return ResponseEntity.notFound().build();
        }
    }
}