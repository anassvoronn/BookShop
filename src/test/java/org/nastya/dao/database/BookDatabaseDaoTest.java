package org.nastya.dao.database;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.BookDao;
import org.nastya.entity.Book;
import org.nastya.entity.Genre;
import org.nastya.utils.DataSourceFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.nastya.utils.ObjectCreator.createBook;

class BookDatabaseDaoTest {
    private static BookDao bookDao;
    private static HikariDataSource dataSource;

    @BeforeAll
    static void beforeAll() {
        DataSourceFactory sourceFactory = new DataSourceFactory();
        sourceFactory.readingFromFile();
        dataSource = sourceFactory.getDataSource();
        bookDao = new BookDatabaseDao(new NamedParameterJdbcTemplate(dataSource));
    }

    @AfterAll
    static void afterAll() {
        dataSource.close();
    }

    @BeforeEach
    void setUp() {
        insertBookToDatabase("Зачарованные", "1980", Genre.FANTASY);
        insertBookToDatabase("Время Приключений", "2008", Genre.ADVENTURE);
        insertBookToDatabase("Оттенки любви", "1882", Genre.NOVEL);
        insertBookToDatabase("Кафе на краю земли", null, Genre.PSYCHOLOGY);
        insertBookToDatabase("Аватар", "1816", Genre.FANTASY);
        insertBookToDatabase("Дорога Юности", null, Genre.NOVEL);
        insertBookToDatabase("Убить Сталкера", null, Genre.HORROR);
        insertBookToDatabase("Неизвестный", "1920", Genre.DETECTIVE);
        insertBookToDatabase("Личная жизнь моего соседа", "1985", Genre.DETECTIVE);
        insertBookToDatabase("Ночная песнь монстра", "1834", Genre.FANTASY);
        insertBookToDatabase("Шампанское и розы", "1961", Genre.PSYCHOLOGY);
        insertBookToDatabase("Шах и мат", "1919", Genre.HORROR);
        insertBookToDatabase("Возмездие", null, Genre.DETECTIVE);
        insertBookToDatabase("19 дней однажды", "1998", Genre.ADVENTURE);
        insertBookToDatabase("Сойти с дороги", "1842", Genre.ADVENTURE);
    }

    @AfterEach
    void tearDown() {
        bookDao.deleteAll();
    }

    @Test
    void insert() {
        List<Book> books = bookDao.findAll();
        assertEquals(15, books.size());

        Book book = createBook("В поисках любви", "1927", Genre.NOVEL);
        bookDao.insert(book);

        books = bookDao.findAll();
        assertEquals(16, books.size());
    }

    @Test
    void findAll() {
        List<Book> books = bookDao.findAll();
        assertFalse(books.isEmpty(), "Books list has to be not empty");
        assertEquals(15, books.size());
    }

    @Test
    void findByTitle() {
        List<Book> books = bookDao.findByTitle("Шах и мат");
        assertEquals(1, books.size());
        for (Book book : books) {
            assertEquals("Шах и мат", book.getTitle());
        }
    }

    @Test
    void deleteById() {
        int idToDelete = 1;
        bookDao.deleteById(idToDelete);
        assertNull(bookDao.findById(idToDelete), "Book should be deleted");
    }

    @Test
    void deletingSeveralBooksByID() {
        int idToDelete1 = 1;
        int idToDelete5 = 5;
        int idToDelete10 = 10;
        int idToDelete15 = 15;
        bookDao.deleteById(idToDelete1);
        bookDao.deleteById(idToDelete5);
        bookDao.deleteById(idToDelete10);
        bookDao.deleteById(idToDelete15);
        assertNull(bookDao.findById(idToDelete1), "Book should be deleted");
        assertNull(bookDao.findById(idToDelete5), "Book should be deleted");
        assertNull(bookDao.findById(idToDelete10), "Book should be deleted");
        assertNull(bookDao.findById(idToDelete15), "Book should be deleted");
    }

    @Test
    void findByTitle_foundTwoBooks() {
        insertBookToDatabase("Кафе на краю земли", "1999", Genre.FANTASY);
        List<Book> books = bookDao.findByTitle("Кафе на краю земли");
        assertEquals(2, books.size());
        for (Book book : books) {
            assertEquals("Кафе на краю земли", book.getTitle());
        }
    }

    @Test
    void save() {
        List<Book> books = bookDao.findByTitle("Убить сталкера");
        assertEquals(1, books.size());
        for (Book book : books) {
            book.setPublishingYear(1875);
            bookDao.save(book);
            Book updatedBook = bookDao.findById(book.getId());
            assertEquals(1875, updatedBook.getPublishingYear());
        }
    }

    @Test
    void findByTitle_thatDoesNotExist() {
        List<Book> books = bookDao.findByTitle("Кодовое имя Анастасия");
        assertEquals(0, books.size());
    }

    @Test
    void deleteById_thatDoesNotExist() {
        int idToDelete = 21;
        bookDao.deleteById(idToDelete);
        assertNull(bookDao.findById(idToDelete), "No such ID exists");
    }

    private void insertBookToDatabase(String title, String publishingYear, Genre genre) {
        Book book = createBook(title, publishingYear, genre);
        bookDao.insert(book);
    }
}