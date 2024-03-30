package org.nastya.service;

import org.nastya.dao.BookDao;
import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Book;
import org.nastya.service.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookMapper bookMapper;

    public List<BookListItemDTO> findAll() {
        List<Book> books = bookDao.findAll();
        log.info("Found '{}' books", books.size());
        return bookMapper.mapToBookListItemDTO(books);
    }

    public BookFormDTO findById(int id) throws BookNotFoundException {
        Book book = bookDao.findById(id);
        if (book == null) {
            log.warn("Book with id '{}' not found", id);
            throw new BookNotFoundException("There is no book with this id " + id);
        }
        log.info("Found book with id '{}'", id);
        return bookMapper.mapToBookFormDTO(book);
    }

    public void deleteBook(int bookId) throws BookNotFoundException {
        Book book = bookDao.findById(bookId);
        if (book == null) {
            log.info("Book with id '{}' was not found", bookId);
            throw new BookNotFoundException("There is no book with this id " + bookId);
        }
        bookDao.deleteById(bookId);
        log.info("Deleted book '{}' with id '{}'", book.getTitle(), book.getId());
    }

    public void addBook(BookFormDTO bookFormDTO) {
        Book book = bookMapper.mapToBook(bookFormDTO);
        bookDao.insert(book);
        log.info("Added book '{}' with id '{}'", book.getTitle(), book.getId());
    }

    public void updateBook(BookFormDTO bookFormDTO) throws BookNotFoundException {
        Book bookEntity = bookMapper.mapToBook(bookFormDTO);
        if (bookEntity == null) {
            log.info("Book with id '{}' was not found", bookFormDTO.getId());
            throw new BookNotFoundException("There is no bookEntity with this id " + bookFormDTO.getId());
        }
        bookDao.save(bookEntity);
        log.info("Updated bookEntity '{}' with id '{}'", bookEntity.getTitle(), bookEntity.getId());
    }

}
