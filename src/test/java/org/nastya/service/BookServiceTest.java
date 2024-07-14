package org.nastya.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.ConnectionFactory.DatabaseConnectionFactory;
import org.nastya.dao.AuthorDao;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.dao.BookDao;
import org.nastya.dao.database.AuthorDatabaseDao;
import org.nastya.dao.database.AuthorToBookDatabaseDao;
import org.nastya.dao.database.BookDatabaseDao;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.dto.BookFormDTO;
import org.nastya.entity.Country;
import org.nastya.entity.Gender;
import org.nastya.entity.Genre;
import org.nastya.service.exception.BookNotFoundException;
import org.nastya.service.mapper.AuthorMapperImpl;
import org.nastya.service.mapper.BookMapperImpl;
import org.nastya.utils.ObjectCreator;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.nastya.entity.Country.ENGLAND;
import static org.nastya.entity.Gender.FEMALE;
import static org.nastya.entity.Gender.MALE;

public class BookServiceTest {

    private BookService bookService;
    private AuthorDao authorDao;
    private BookDao bookDao;
    private AuthorToBookDao authorToBookDao;
    private int bookId;
    private final DatabaseConnectionFactory connectionFactory = new DatabaseConnectionFactory();

    @BeforeEach
    void setUp() {
        connectionFactory.readingFromFile();
        authorDao = new AuthorDatabaseDao(connectionFactory);
        bookDao = new BookDatabaseDao(connectionFactory);
        authorToBookDao = new AuthorToBookDatabaseDao(connectionFactory);
        bookService = new BookService(
                bookDao,
                new BookMapperImpl(),
                authorDao,
                authorToBookDao,
                new AuthorMapperImpl()
        );
        bookId = bookDao.insert(
                ObjectCreator.createBook(
                        "Властелин камня",
                        "1985",
                        Genre.ADVENTURE
                )
        );
        int author1Id = authorDao.insert(
                ObjectCreator.createAuthor(
                        "Девид Джонс",
                        "1939-04-25",
                        "2014-10-11",
                        Gender.MALE,
                        Country.ENGLAND
                )
        );
        int author2Id = authorDao.insert(
                ObjectCreator.createAuthor(
                        "Эмма Уотсон",
                        "1944-10-05",
                        "2018-01-28",
                        FEMALE,
                        Country.ENGLAND
                )
        );
        int author3Id = authorDao.insert(
                ObjectCreator.createAuthor(
                        "Вильям Ви",
                        "1942-08-21",
                        "2010-12-01",
                        Gender.MALE,
                        Country.ENGLAND
                )
        );
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                author1Id, bookId
        ));
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                author2Id, bookId
        ));
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                author3Id, bookId
        ));
    }

    @AfterEach
    void tearDown(){
        bookDao.deleteAll();
        authorDao.deleteAll();
        authorToBookDao.deleteAll();
    }

    @Test
    void findById() throws BookNotFoundException {
        BookFormDTO dto = bookService.findById(bookId);
        assertEquals("Властелин камня", dto.getTitle());
        assertEquals(1985, dto.getPublishingYear());
        assertEquals(Genre.ADVENTURE, dto.getGenre());

        assertEquals(3, dto.getAuthors().size());

        dto.getAuthors().sort(Comparator.comparing(AuthorListItemDTO::getName));

        assertEquals("Вильям Ви", dto.getAuthors().get(0).getName());
        assertEquals(LocalDate.of(1942, 8, 21), dto.getAuthors().get(0).getBirthDate());
        assertEquals(LocalDate.of(2010, 12, 1), dto.getAuthors().get(0).getDeathDate());
        assertEquals(ENGLAND, dto.getAuthors().get(0).getCountry());
        assertEquals(MALE, dto.getAuthors().get(0).getGender());
        assertEquals(68, dto.getAuthors().get(0).getAge());

        assertEquals("Девид Джонс", dto.getAuthors().get(1).getName());
        assertEquals(LocalDate.of(1939, 4, 25), dto.getAuthors().get(1).getBirthDate());
        assertEquals(LocalDate.of(2014, 10, 11), dto.getAuthors().get(1).getDeathDate());
        assertEquals(ENGLAND, dto.getAuthors().get(1).getCountry());
        assertEquals(MALE, dto.getAuthors().get(1).getGender());
        assertEquals(75, dto.getAuthors().get(1).getAge());

        assertEquals("Эмма Уотсон", dto.getAuthors().get(2).getName());
        assertEquals(LocalDate.of(1944, 10, 5), dto.getAuthors().get(2).getBirthDate());
        assertEquals(LocalDate.of(2018, 1, 28), dto.getAuthors().get(2).getDeathDate());
        assertEquals(ENGLAND, dto.getAuthors().get(2).getCountry());
        assertEquals(FEMALE, dto.getAuthors().get(2).getGender());
        assertEquals(73, dto.getAuthors().get(2).getAge());
    }
}
