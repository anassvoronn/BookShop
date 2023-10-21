package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;
import org.nastya.entity.Country;
import org.nastya.entity.Gender;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.nastya.entity.Country.ENGLAND;
import static org.nastya.entity.Country.RUSSIA;
import static org.nastya.entity.Country.UKRAINE;
import static org.nastya.entity.Country.USA;
import static org.nastya.entity.Gender.FEMALE;
import static org.nastya.entity.Gender.MALE;

class AuthorDatabaseDaoTest {
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDatabaseDao();
        insertAuthorToDatabase("Александр Грин", "1880-08-23", MALE, RUSSIA);
        insertAuthorToDatabase("Александр Пушкин", "1799-06-06", MALE, RUSSIA);
        insertAuthorToDatabase("Владимир Маяковский", "1893-07-19", MALE, RUSSIA);
        insertAuthorToDatabase("Джейн Остин", "1775-12-16", FEMALE, ENGLAND);
        insertAuthorToDatabase("Марк Твен", "1835-11-30", MALE, USA);
        insertAuthorToDatabase("Эмели Бронте", "1818-07-30", FEMALE, ENGLAND);
        insertAuthorToDatabase("Фёдор Достоевский", "1821-11-11", MALE, RUSSIA);
        insertAuthorToDatabase("Агата Кристи", "1890-09-15", FEMALE, ENGLAND);
        insertAuthorToDatabase("Харпер Ли", "1926-04-28", FEMALE, USA);
        insertAuthorToDatabase("Михаил Булгаков", "1891-05-15", MALE, UKRAINE);
    }

    @AfterEach
    void tearDown() {
        authorDao.deleteAll();
    }

    @Test
    void insert() {
        List<Author> authors = authorDao.findAll();
        assertEquals(10, authors.size());

        Author author = createAuthor("Александр Грин", "1880-08-25", MALE, RUSSIA);
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
        insertAuthorToDatabase("Александр Пушкин", "1799-10-06", MALE, USA);
        List<Author> authors = authorDao.findByName("Александр Пушкин");
        assertEquals(2, authors.size());
        for (Author author : authors) {
            assertEquals("Александр Пушкин", author.getName());
        }
    }

    @Test
    void findByGender() {
        List<Author> authors = authorDao.findByGender(FEMALE);
        assertEquals(4, authors.size());
        for (Author author : authors) {
            assertEquals(FEMALE, author.getGender());
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
        List<Author> authors = authorDao.findByName("Эмели Бронте");
        for (Author author : authors) {
            author.setBirthDate(LocalDate.of(1990, 3, 3));
            authorDao.save(author);
            Author updatedAuthor = authorDao.findById(author.getId());
            assertEquals(LocalDate.of(1990, 3, 3), updatedAuthor.getBirthDate());
        }
    }

    private void insertAuthorToDatabase(String name, String birthDate, Gender gender, Country country) {
        Author author = createAuthor(name, birthDate, gender, country);
        authorDao.insert(author);
    }

    private Author createAuthor(String name, String birthDate, Gender gender, Country country) {
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
        List<Author> authors = authorDao.findByGenderOrByCountry(MALE, RUSSIA);
        assertEquals(6, authors.size());
        assertEquals(MALE, authors.get(0).getGender());
        assertEquals(RUSSIA, authors.get(0).getCountry());
    }

    @Test
    public void findByGenderAndByBirthDate() {
        List<Author> authors = authorDao.findByGenderAndByBirthDate(FEMALE, "1926-04-28");
        assertEquals(1, authors.size());
        Author author = authors.get(0);
        assertEquals("Харпер Ли", author.getName());
        assertEquals("1926-04-28", author.getBirthDateAsString());
        assertEquals(FEMALE, author.getGender());
        assertEquals(USA, author.getCountry());
    }

    @Test
    public void findByGenderAndByBirthDate_nothingFound() {
        List<Author> authors = authorDao.findByGenderAndByBirthDate(FEMALE, "1926-04-21");
        assertEquals(0, authors.size());
    }

    @Test
    public void findByGenderAndByBirthDate_found2Authors() {
        insertAuthorToDatabase("Джейн Остин", "1926-04-28", FEMALE, ENGLAND);

        List<Author> authors = authorDao.findByGenderAndByBirthDate(FEMALE, "1926-04-28");

        assertEquals(2, authors.size());

        Author author1 = authors.get(0);
        assertEquals("Харпер Ли", author1.getName());
        assertEquals("1926-04-28", author1.getBirthDateAsString());
        assertEquals(FEMALE, author1.getGender());
        assertEquals(USA, author1.getCountry());

        Author author2 = authors.get(1);
        assertEquals("Джейн Остин", author2.getName());
        assertEquals("1926-04-28", author2.getBirthDateAsString());
        assertEquals(FEMALE, author2.getGender());
        assertEquals(ENGLAND, author2.getCountry());
    }

    @Test
    public void deleteAllByGender() {
        List<Author> authors = authorDao.findByGender(MALE);
        authorDao.deleteAllByGender(MALE);
        assertEquals(0, authors.size());
    }

    @Test
    public void getCountByGender() {
        int count = authorDao.getCountByGender(FEMALE);
        assertEquals(4, count);
    }

    @Test
    public void getCount() {
        int count = authorDao.getCount();
        List<Author> authors = authorDao.findAll();
        assertEquals(count, authors.size());
    }

    @Test
    public void getCount_() {
        insertAuthorToDatabase("Джейн Остин", "1775-12-16", FEMALE, ENGLAND);
        insertAuthorToDatabase("Марк Твен", "1835-11-30", MALE, USA);
        insertAuthorToDatabase("Эмели Бронте", "1818-07-30", FEMALE, ENGLAND);

        int count = authorDao.getCount();
        List<Author> authors = authorDao.findAll();
        assertEquals(count, authors.size());
    }

    @Test
    public void getBiggestId() {
        List<Author> authors = authorDao.findByName("Михаил Булгаков");
        for (Author author : authors) {
            int biggestId = authorDao.getBiggestId();
            assertEquals(biggestId, author.getId());
        }
    }
}