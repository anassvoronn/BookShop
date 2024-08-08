package org.nastya.dao.database;

import org.nastya.dao.BookViewsDao;
import org.nastya.entity.BookViews;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookViewsDatabaseDao implements BookViewsDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String BOOK_ID = "bookId";
    static final String VIEW_COUNT = "viewCount";

    private static final String BOOK_VIEWS_COUNTER = "UPDATE book_views SET viewCount = viewCount + 1 WHERE bookId = :bookId";

    public BookViewsDatabaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void incrementViewCount(BookViews bookViews) {
        jdbcTemplate.update(BOOK_VIEWS_COUNTER, new MapSqlParameterSource()
                .addValue(BOOK_ID, bookViews.getBookId())
                .addValue(VIEW_COUNT, bookViews.getViewsCount()));
    }
}