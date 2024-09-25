package org.nastya.dao.database;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.nastya.dao.BookViewsDao;
import org.nastya.entity.BookViews;
import org.nastya.utils.DataSourceFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nastya.utils.ObjectCreator.createBookViews;

class BookViewsDatabaseDaoTest {
    private static BookViewsDao bookViewsDao;
    private static HikariDataSource dataSource;

    @BeforeAll
    static void beforeAll() {
        DataSourceFactory sourceFactory = new DataSourceFactory();
        sourceFactory.readingFromFile();
        dataSource = sourceFactory.getDataSource();
        bookViewsDao = new BookViewsDatabaseDao(new NamedParameterJdbcTemplate(dataSource));
    }

    @AfterAll
    static void afterAll() {
        bookViewsDao.deleteAll();
        dataSource.close();
    }

    @BeforeEach
    void setUp() {
        insertBookViews(1, 0);
        insertBookViews(2, 5);
        insertBookViews(3, 1);
        insertBookViews(4, 20);
        insertBookViews(5, 100);
    }

    @Test
    void test() {
        insertBookViews(9, 0);
        int countBefore = bookViewsDao.getViewsCountByBookId(9);
        assertEquals(0, countBefore);

        bookViewsDao.incrementViewsCountByBookId(9);

        int countAfter = bookViewsDao.getViewsCountByBookId(9);
        assertEquals(1, countAfter);

    }

    private void insertBookViews(int bookId, int viewCount) {
        BookViews bookViews = createBookViews(bookId, viewCount);
        bookViewsDao.insert(bookViews);
    }
}