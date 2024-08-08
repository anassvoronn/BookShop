package org.nastya.dao.database;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.nastya.dao.BookViewsDao;
import org.nastya.entity.BookViews;
import org.nastya.utils.DataSourceFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
        dataSource.close();
    }

    @BeforeEach
    void setUp() {
        insertViewsCount(1, 15);
        insertViewsCount(2, 0);
        insertViewsCount(3, 100);
        insertViewsCount(4, 60);
        insertViewsCount(5, 1);
    }

    private void insertViewsCount(int bookId, int viewsCount) {
        BookViews bookViews = createBookViews(bookId, viewsCount);
        bookViewsDao.incrementViewCount(bookViews);
    }
}