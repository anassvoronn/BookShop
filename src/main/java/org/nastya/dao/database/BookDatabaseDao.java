package org.nastya.dao.database;

import org.nastya.dao.BookDao;
import org.nastya.dao.builder.SearchDetails;
import org.nastya.entity.Book;
import org.nastya.entity.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookDatabaseDao implements BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String ID = "id";
    static final String TITLE = "title";
    static final String PUBLISHING_YEAR = "publishingYear";
    static final String GENRE = "genre";

    private static final String REQUEST_BY_ID = "SELECT id, title, publishingYear, genre FROM books WHERE id = :id";
    private static final String SELECT = "SELECT id, title, publishingYear, genre FROM books";
    private static final String INSERT = "INSERT INTO books (title, publishingYear, genre) VALUES (:title, :publishingYear, :genre) RETURNING id";
    private static final String UPDATE = "UPDATE books SET title = :title, publishingYear = :publishingYear, genre = :genre WHERE id = :id";
    private static final String DELETION_BY_ID = "DELETE FROM books WHERE id = :id";
    private static final String DELETE_FROM_BOOKS = "DELETE FROM books";
    private static final String SELECT_BY_NAME = "SELECT * FROM books WHERE title= :title";

    public BookDatabaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book findById(int id) {
        try {
            return jdbcTemplate.queryForObject(REQUEST_BY_ID,
                    new MapSqlParameterSource().addValue("id", id),
                    (rs, rowNum) -> bindBook(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(SELECT,
                (rs, rowNum) -> bindBook(rs));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return jdbcTemplate.query(SELECT_BY_NAME,
                new MapSqlParameterSource().addValue("title", title),
                (rs, rowNum) -> bindBook(rs));
    }

    @Override
    public int insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT,
                new MapSqlParameterSource()
                        .addValue("title", book.getTitle())
                        .addValue("publishingYear", book.getPublishingYear())
                        .addValue("genre", book.getGenre().name()), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update(UPDATE,
                new MapSqlParameterSource()
                        .addValue("title", book.getTitle())
                        .addValue("publishingYear", book.getPublishingYear())
                        .addValue("genre", book.getGenre().name())
                        .addValue("id", book.getId()));
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETION_BY_ID,
                new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_FROM_BOOKS, new MapSqlParameterSource());
    }

    @Override
    public List<Book> findByGenreAndTitleAndPublishingYearAndAuthorId(SearchDetails searchDetails) {
        String searchTerm = searchDetails.getTitle() != null ? "%" + searchDetails.getTitle().replace(" ", "%") + "%" : null;
        String sql = "SELECT * FROM books";
        String where = "";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (searchDetails.getGenre() != null) {
            where += "genre = :genre";
            params.addValue("genre", searchDetails.getGenre().name());
        }
        if (searchDetails.getTitle() != null) {
            if (!where.isEmpty()) {
                where += " AND ";
            }
            where += "title ILIKE :title";
            params.addValue("title", searchTerm);
        }
        String publishingYear = removeSpaces(searchDetails.getPublishingYear());
        if (!publishingYear.isEmpty()) {
            if (!where.isEmpty()) {
                where += " AND ";
            }
            String[] publishingYearSeparators = publishingYear.split(",");
            List<String> conditions = new ArrayList<>();
            for (String separator : publishingYearSeparators) {
                if (separator.contains("-")) {
                    String[] intervals = separator.split("-");
                    if (intervals.length == 2) {
                        conditions.add("publishingYear BETWEEN :startYear" + intervals[0] + " AND :endYear" + intervals[1]);
                        params.addValue("startYear" + intervals[0], Integer.parseInt(intervals[0]));
                        params.addValue("endYear" + intervals[1], Integer.parseInt(intervals[1]));
                    }
                } else {
                    conditions.add("publishingYear = :year" + separator);
                    params.addValue("year" + separator, Integer.parseInt(separator));
                }
            }
            if (!conditions.isEmpty()) {
                where += "(" + String.join(" OR ", conditions) + ")";
            }
        }
        if (searchDetails.getAuthorId() != null) {
            if (!where.isEmpty()) {
                where += " AND ";
            }
            where += "id IN (SELECT ab.bookId FROM author_To_Book ab WHERE ab.authorId = :authorId)";
            params.addValue("authorId", searchDetails.getAuthorId());
        }
        if (!where.isEmpty()) {
            sql += " WHERE " + where;
        }
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> bindBook(rs));
    }

    private String removeSpaces(String publishingYear) {
        if (publishingYear == null) {
            return "";
        }
        return publishingYear.replace(" ", "");
    }

    private Book bindBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(ID));
        book.setTitle(rs.getString(TITLE));
        book.setPublishingYear(rs.getInt(PUBLISHING_YEAR));
        book.setGenre(Genre.valueOf(rs.getString(GENRE)));
        return book;
    }
}
