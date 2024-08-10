package org.nastya.dao.database;

import org.nastya.dao.BookViewsDao;
import org.nastya.entity.BookViews;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BookViewsDatabaseDao implements BookViewsDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BOOK_ID = "bookId";
    private static final String VIEW_COUNT = "viewCount";

    private static final String INSERT = "INSERT INTO book_views (bookId, viewCount) VALUES (:bookId, :viewCount)";
    private static final String GET_VIEWS_BY_BOOK_ID = "SELECT viewCount FROM book_views WHERE bookId = :bookId";
    private static final String INCREMENT_VIEWS_COUNT_BY_BOOK_ID = "UPDATE book_views SET viewCount = viewCount + 1 WHERE bookId = :bookId";

    public static final int DEFAULT_COUNT_IF_NOT_FOUND = -1;

    public BookViewsDatabaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(BookViews bookViews) {
        jdbcTemplate.update(INSERT, new MapSqlParameterSource()
                .addValue(BOOK_ID, bookViews.getBookId())
                .addValue(VIEW_COUNT, bookViews.getViewsCount()));
    }

    @Override
    public void incrementViewsCountByBookId(int bookId) {
        jdbcTemplate.update(INCREMENT_VIEWS_COUNT_BY_BOOK_ID, new MapSqlParameterSource()
                .addValue(BOOK_ID, bookId));
    }

    @Override
    public int getViewsCountByBookId(int bookId) {
        Integer count;
        try {
            count = jdbcTemplate.queryForObject(GET_VIEWS_BY_BOOK_ID, new MapSqlParameterSource()
                    .addValue(BOOK_ID, bookId), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return DEFAULT_COUNT_IF_NOT_FOUND;
        }
        return Objects.requireNonNullElse(count, DEFAULT_COUNT_IF_NOT_FOUND);
    }
}