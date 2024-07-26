package org.nastya.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorDao;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.dao.BookDao;
import org.nastya.dao.database.AuthorDatabaseDao;
import org.nastya.dao.database.AuthorToBookDatabaseDao;
import org.nastya.dao.database.BookDatabaseDao;
import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.service.mapper.BookMapperImpl;
import org.nastya.utils.DataSourceFactory;
import org.nastya.utils.ObjectCreator;
import org.nastya.entity.Genre;
import org.nastya.service.exception.AuthorNotFoundException;
import org.nastya.service.mapper.AuthorMapperImpl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.nastya.entity.Country.USA;
import static org.nastya.entity.Gender.MALE;

class AuthorServiceTest {

    private AuthorService authorService;
    private AuthorDao authorDao;
    private BookDao bookDao;
    private AuthorToBookDao authorToBookDao;
    private int authorId;

    @BeforeEach
    void setUp() {
        DataSourceFactory factory = new DataSourceFactory();
        factory.readingFromFile();
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(factory.getDataSource());
        authorDao = new AuthorDatabaseDao(jdbcTemplate);
        bookDao = new BookDatabaseDao(jdbcTemplate);
        authorToBookDao = new AuthorToBookDatabaseDao(jdbcTemplate);
        authorService = new AuthorService(
                authorDao,
                authorToBookDao,
                new AuthorMapperImpl(),
                bookDao,
                new BookMapperImpl()
        );
        authorId = authorDao.insert(
                ObjectCreator.createAuthor(
                        "Джон Дой",
                        "1980-05-15",
                        "2020-03-10",
                        MALE,
                        USA
                )
        );
        int book1Id = bookDao.insert(
                ObjectCreator.createBook(
                        "The Hobbit",
                        "1937",
                        Genre.FANTASY
                )
        );
        int book2Id = bookDao.insert(
                ObjectCreator.createBook(
                        "Время Приключений",
                        "2008",
                        Genre.ADVENTURE
                )
        );
        int book3Id = bookDao.insert(
                ObjectCreator.createBook(
                        "Оттенки любви",
                        "1882",
                        Genre.NOVEL
                )
        );
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                authorId, book1Id
        ));
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                authorId, book2Id
        ));
        authorToBookDao.insert(ObjectCreator.createAuthorToBook(
                authorId, book3Id
        ));
    }

    @AfterEach
    void tearDown() {
        authorDao.deleteAll();
        bookDao.deleteAll();
        authorToBookDao.deleteAll();
    }

    @Test
    void findById() throws AuthorNotFoundException {
        AuthorFormDTO dto = authorService.findById(authorId);
        assertEquals("Джон Дой", dto.getName());
        assertEquals(LocalDate.of(1980, 5, 15), dto.getBirthDate());
        assertEquals(LocalDate.of(2020, 3, 10), dto.getDeathDate());
        assertEquals(USA, dto.getCountry());
        assertEquals(MALE, dto.getGender());
        assertEquals(39, dto.getAge());

        assertEquals(3, dto.getBooks().size());

        dto.getBooks().sort(Comparator.comparing(BookListItemDTO::getTitle));

        assertEquals(Genre.FANTASY, dto.getBooks().get(0).getGenre());
        assertEquals("The Hobbit", dto.getBooks().get(0).getTitle());
        assertEquals(1937, dto.getBooks().get(0).getPublishingYear());

        assertEquals(Genre.ADVENTURE, dto.getBooks().get(1).getGenre());
        assertEquals("Время Приключений", dto.getBooks().get(1).getTitle());
        assertEquals(2008, dto.getBooks().get(1).getPublishingYear());

        assertEquals(Genre.NOVEL, dto.getBooks().get(2).getGenre());
        assertEquals("Оттенки любви", dto.getBooks().get(2).getTitle());
        assertEquals(1882, dto.getBooks().get(2).getPublishingYear());
    }
}