package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthorDatabaseDaoTest {
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDatabaseDao();
        createAuthor("Александр Грин", "1880-08-23", "Мужской", "Россия");
        createAuthor("Александр Пушкин", "1799-06-06", "Мужской", "Россия");
        createAuthor("Владимир Маяковский", "1893-07-19", "Мужской", "Россия");
        createAuthor("Джейн Остин", "1775-12-16", "Женский", "Англия");
        createAuthor("Марк Твен", "1835-11-30", "Мужской", "США");
        createAuthor("Эмели Бронте", "1818-07-30", "Женский", "Англия");
        createAuthor("Фёдор Достоевский", "1821-11-11", "Мужской", "Россия");
        createAuthor("Агата Кристи", "1890-09-15", "Женский", "Англия");
        createAuthor("Харпер Ли", "1926-04-28", "Женский", "США");
        createAuthor("Михаил Булгаков", "1891-05-15", "Мужской", "Украина");
    }

    @AfterEach
    void tearDown() {
        authorDao.deleteAll();
    }

    @Test
    void insert() {
        Author author = new Author();
        author.setName("Александр Грин");
        LocalDate date = author.getBirthDate();
        author.setBirthDate(date);
        author.setGender("Мужской");
        author.setCountry("Россия");
        int generatedId = authorDao.insert(author);
        assertNotEquals(0, generatedId);
    }

    @Test
    void findAll() {
        List<Author> authors = authorDao.findAll();

    }

    private void createAuthor(String name, String birthDate, String gender, String country) {
        Author author = new Author();
        author.setName(name);
        LocalDate date = LocalDate.parse(birthDate);
        author.setBirthDate(date);
        author.setGender(gender);
        author.setCountry(country);
        authorDao.insert(author);
    }
}