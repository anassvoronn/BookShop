package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.dao.BookDao;
import org.nastya.dao.BookViewsDao;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Author;
import org.nastya.entity.AuthorToBook;
import org.nastya.entity.Book;
import org.nastya.entity.BookViews;
import org.nastya.service.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookDao bookDao;
    private final BookMapper bookMapper;
    private final AuthorDao authorDao;
    private final AuthorToBookDao authorToBookDao;
    private final AuthorMapper authorMapper;
    private final BookViewsDao bookViewsDao;

    public BookService(final BookDao bookDao,
                       final BookMapper bookMapper,
                       final AuthorDao authorDao,
                       final AuthorToBookDao authorToBookDao,
                       final AuthorMapper authorMapper,
                       final BookViewsDao bookViewsDao) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
        this.authorDao = authorDao;
        this.authorToBookDao = authorToBookDao;
        this.authorMapper = authorMapper;
        this.bookViewsDao = bookViewsDao;
    }

    public List<BookListItemDTO> findAll() {
        List<Book> books = bookDao.findAll();
        log.info("Found '{}' books", books.size());
        return bookMapper.mapToBookListItemDTO(books);
    }

    private int calculateAge(LocalDate dateOfBirth, LocalDate deathDate) {
        if (dateOfBirth == null) {
            log.info("Date of birth was not provided. Returning 0");
            return 0;
        }
        if (deathDate == null) {
            LocalDate now = LocalDate.now();
            log.info("Date of death was not provided. Using current date '{}'", now);
            return Period.between(dateOfBirth, now).getYears();
        }
        return Period.between(dateOfBirth, deathDate).getYears();
    }

    public BookFormDTO findById(int id) throws BookNotFoundException {
        Book book = bookDao.findById(id);
        if (book == null) {
            log.warn("Book with id '{}' not found", id);
            throw new BookNotFoundException("There is no book with this id " + id);
        }
        log.info("Found book with id '{}'", id);
        BookFormDTO dto = bookMapper.mapToBookFormDTO(book);
        List<AuthorToBook> authorToBooks = authorToBookDao.findByBookId(id);
        List<AuthorListItemDTO> authorListDTO = new ArrayList<>();
        for (AuthorToBook authorToBook : authorToBooks) {
            Author author = authorDao.findById(authorToBook.getAuthorId());
            if (author != null) {
                AuthorListItemDTO authorListItemDTO = authorMapper.mapToAuthorListItemDTO(author);
                LocalDate birthDate = authorListItemDTO.getBirthDate();
                LocalDate deathDate = authorListItemDTO.getDeathDate();
                int age = calculateAge(birthDate, deathDate);
                authorListItemDTO.setAge(age);
                authorListDTO.add(authorListItemDTO);
            }
        }
        dto.setAuthors(authorListDTO);
        return dto;
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

    public void incrementViewCount(int bookId) throws BookNotFoundException {
        Book book = bookDao.findById(bookId);
        if (book == null) {
            log.warn("Book with id '{}' not found for incrementing view count", bookId);
            throw new BookNotFoundException("There is no book with this id " + bookId);
        }
        BookFormDTO bookFormDTO = bookMapper.mapToBookFormDTO(book);

        int newViewsCount = bookFormDTO.getViewsCount() + 1;
        bookFormDTO.setViewsCount(newViewsCount);

        Book updatedBook = bookMapper.mapToBook(bookFormDTO);

        BookViews bookViews = new BookViews();
        bookViews.setBookId(updatedBook.getId());
        bookViews.setViewsCount(newViewsCount);
        bookViewsDao.incrementViewCount(bookViews);
        log.info("Incremented view count for book with id '{}'. New view count: {}",
                updatedBook.getId(), newViewsCount);
    }
}
