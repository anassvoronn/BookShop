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
        bookDao.deleteAll();
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
        List<Book> books = bookDao.findByTitle("Убить Сталкера");
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

    @Test
    void findByPublishingYear_case1(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, null, "1816-1882");
        assertEquals(4, books.size());
    }

    @Test
    void findByPublishingYear_case2(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, null, "1816-1882, 2008");
        assertEquals(5, books.size());
    }

    @Test
    void findByPublishingYear_case3(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, null, "1816-1882, 2008, 1961-1985");
        assertEquals(8, books.size());
    }


    @Test
    void findByPublishingYearAndGenre_case4(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1800-1900");
        assertEquals(2, books.size());
    }

    @Test
    void findByPublishingYearAndGenre_case4_withOverlapping(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1800-1875, 1825-1900");
        assertEquals(2, books.size());
    }

    @Test
    void findByPublishingYearAndGenre_case4_withMinimalOverlapping(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1800-1850, 1850-1900");
        assertEquals(2, books.size());
    }
    
    @Test
    void findByPublishingYearAndGenre_case4_withBigOverlapping(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1800-1850, 1800-1900");
        assertEquals(2, books.size());
    }

    @Test
    void findByPublishingYearAndGenre_case5(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.NOVEL, null, "1805-1885, 2010");
        assertEquals(1, books.size());
    }

    @Test
    void findByPublishingYearAndGenre_case6(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1810-1840, 1919, 1960-1990");
        assertEquals(3, books.size());
    }
    
    @Test
    void findByPublishingYearAndGenre_case7_withSpace(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, null, "1810  -  1840  , 1960 -  1990");
        assertEquals(3, books.size());
    }

    @Test
    void findByPublishingYearAndGenreAndTitle_case7(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, "р    ", "1800-1900");
        assertEquals(2, books.size());
    }

    @Test
    void findByPublishingYearAndGenreAndTitle_case8(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.ADVENTURE, "   д", "1830-1999, 2008");
        assertEquals(2, books.size());
    }

    @Test
    void findByPublishingYearAndTitle_case9(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, "   а   ", "1810-1840, 1919, 1960-1990");
        assertEquals(6, books.size());
    }
    
    @Test
    void findByPublishingYearAndTitle_case9_withSpace(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, "   а   ", "  1810  -  1840 ,   1919 ,   1960 - 1990 ");
        assertEquals(6, books.size());
    }

    @Test
    void findByGenreAndByTitle_case1(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.HORROR, "и", "1919");
        assertEquals(1, books.size());
    }
    
    @Test
    void findByGenreAndByTitle_case1_twoIdenticalDates(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.HORROR, "и", " 1919 ,  1919 ");
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case2(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.NOVEL, "И", null);
        assertEquals(2, books.size());
    }

    @Test
    void findByGenreAndByTitle_case3(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.FANTASY, "аватар", "1816");
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case4() {
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.PSYCHOLOGY, "  на    краю  ", null);
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case1_whenGenreIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, "и", null);
        assertEquals(11, books.size());
    }

    @Test
    void findByGenreAndByTitle_case2_whenGenreIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, "И", "1842");
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case3_whenGenreIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, "дорог", null);
        assertEquals(2, books.size());
    }

    @Test
    void findByGenreAndByTitle_case1_whenTitleIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.HORROR, null, "1919");
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case2_whenTitleIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.NOVEL, null, "1882");
        assertEquals(1, books.size());
    }

    @Test
    void findByGenreAndByTitle_case3_whenTitleIsNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.NOVEL, null, null);
        assertEquals(2, books.size());
    }

    @Test
    void findByGenreAndByTitle_whenAllNull(){
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(null, null, null);
        assertEquals(15, books.size());
    }

    @Test
    void findByGenreAndTitleThatDoesNotExist() {
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear(Genre.NOVEL, "Киллер", "1885");
        assertEquals(0, books.size());
    }

    @Test
    void findByTitleAndGenreThatDoesNotExist() {
        List<Book> books = bookDao.findByGenreAndByTitleAndByPublishingYear( Genre.COMEDY , "", "1825");
        assertEquals(0, books.size());
    }

    private void insertBookToDatabase(String title, String publishingYear, Genre genre) {
        Book book = createBook(title, publishingYear, genre);
        bookDao.insert(book);
    }
}