package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDatabaseDaoTest {
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDatabaseDao();
        insertAuthorToDatabase("Александр Грин", "1880-08-23", "Мужской", "Россия");
        insertAuthorToDatabase("Александр Пушкин", "1799-06-06", "Мужской", "Россия");
        insertAuthorToDatabase("Владимир Маяковский", "1893-07-19", "Мужской", "Россия");
        insertAuthorToDatabase("Джейн Остин", "1775-12-16", "Женский", "Англия");
        insertAuthorToDatabase("Марк Твен", "1835-11-30", "Мужской", "США");
        insertAuthorToDatabase("Эмели Бронте", "1818-07-30", "Женский", "Англия");
        insertAuthorToDatabase("Фёдор Достоевский", "1821-11-11", "Мужской", "Россия");
        insertAuthorToDatabase("Агата Кристи", "1890-09-15", "Женский", "Англия");
        insertAuthorToDatabase("Харпер Ли", "1926-04-28", "Женский", "США");
        insertAuthorToDatabase("Михаил Булгаков", "1891-05-15", "Мужской", "Украина");
    }

    @AfterEach
    void tearDown() {
        authorDao.deleteAll();
    }

    @Test
    void insert() {
        List<Author> authors = authorDao.findAll();
        assertEquals(10, authors.size());

        Author author = createAuthor("Александр Грин", "1880-08-25", "Мужской", "Россия");
        authorDao.insert(author);

        authors = authorDao.findAll();
        assertEquals(11, authors.size());
    }

    @Test
    void findByName() {
        List<Author> authors = authorDao.findByName("Александр Пушкин");
        assertEquals(1, authors.size());
        for (Author author : authors) {
            assertEquals("Александр Пушкин", author.getName());
        }
    }

    @Test
    void findByName_thatDoesNotExist() {
        List<Author> authors = authorDao.findByName("Александр Домогаров");
        assertEquals(0, authors.size());
    }

    @Test
    void findByName_found2Authors() {
        insertAuthorToDatabase("Александр Пушкин", "1799-10-06", "Мужской", "США");
        List<Author> authors = authorDao.findByName("Александр Пушкин");
        assertEquals(2, authors.size());
        for (Author author : authors) {
            assertEquals("Александр Пушкин", author.getName());
        }
    }

    @Test
    void findByGender() {
        List<Author> authors = authorDao.findByGender("Женский");
        assertEquals(4, authors.size());
        for (Author author : authors) {
            assertEquals("Женский", author.getGender());
        }
    }

    @Test
    void findAll() {
        List<Author> authors = authorDao.findAll();
        assertFalse(authors.isEmpty(), "Authors list has to be not empty");
        assertEquals(10, authors.size());
    }

    @Test
    void deleteById() {
        int idToDelete = 1;
        authorDao.deleteById(idToDelete);
        assertNull(authorDao.findById(idToDelete), "Author should be deleted");
    }

    @Test
    void save() {
        // Author author = authorDao.findByName("Эмели Бронте"); //поменять на лист
        //author.setBirthDate(LocalDate.of(1990, 3, 3));
        // authorDao.save(author);
        // Author updatedAuthor = authorDao.findById(author.getId());
        //  assertEquals(LocalDate.of(1990, 3, 3), updatedAuthor.getBirthDate());
    }

    private void insertAuthorToDatabase(String name, String birthDate, String gender, String country) {
        Author author = createAuthor(name, birthDate, gender, country);
        authorDao.insert(author);
    }

    private Author createAuthor(String name, String birthDate, String gender, String country) {
        Author author = new Author();
        author.setName(name);
        LocalDate date = LocalDate.parse(birthDate);
        author.setBirthDate(date);
        author.setGender(gender);
        author.setCountry(country);
        return author;
    }

    @Test
    public void findByGenderOrByCountry() {
        List<Author> authors = authorDao.findByGenderOrByCountry("Мужской", "Россия");
        assertEquals(6, authors.size());
        assertEquals("Мужской", authors.get(0).getGender());
        assertEquals("Россия", authors.get(0).getCountry());
    }

    @Test
    public void findByGenderAndByBirthDate() {
        List<Author> authors = authorDao.findByGenderAndByBirthDate("Женский", "1926-04-28");
        assertEquals(1, authors.size());
        assertEquals("Харпер Ли", authors.get(0).getName());
        assertEquals("1926-04-28", String.valueOf(authors.get(0).getBirthDate()));
        assertEquals("Женский", authors.get(0).getGender());
        assertEquals("США", authors.get(0).getCountry());
    }
}