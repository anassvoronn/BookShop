package org.nastya.dao.database;

import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class AuthorToBookDatabaseDao implements AuthorToBookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String AUTHOR_ID = "authorId";
    static final String BOOK_ID = "bookId";

    private static final String SELECT_AUTHOR_ID = "SELECT * FROM author_To_Book WHERE authorId = :authorId";
    private static final String SELECT_BOOK_ID = "SELECT * FROM author_To_Book WHERE bookId = :bookId";
    private static final String INSERT = "INSERT INTO author_To_Book (authorId, bookId) VALUES (:authorId, :bookId)";
    private static final String DELETE_FROM_AUTHOR_TO_BOOK = "DELETE FROM author_To_Book";
    private static final String DELETE_BY_BOOK_ID = "DELETE FROM author_To_Book WHERE bookId = :bookId";
    private static final String DELETE_BY_AUTHOR_ID = "DELETE FROM author_To_Book WHERE authorId = :authorId";

    public AuthorToBookDatabaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AuthorToBook> findByAuthorId(int id) {
        return jdbcTemplate.query(SELECT_AUTHOR_ID,
                new MapSqlParameterSource().addValue("authorId", id),
                (rs, rowNum) -> bindAuthorToBook(rs));
    }

    @Override
    public List<AuthorToBook> findByBookId(int id) {
        return jdbcTemplate.query(SELECT_BOOK_ID,
                new MapSqlParameterSource().addValue("bookId", id),
                (rs, rowNum) -> bindAuthorToBook(rs));
    }

    @Override
    public int insert(AuthorToBook authorToBook) {
        return jdbcTemplate.update(INSERT,
                new MapSqlParameterSource()
                        .addValue("authorId", authorToBook.getAuthorId())
                        .addValue("bookId", authorToBook.getBookId()));
    }

    @Override
    public void deleteByAuthorId(int id) {
        jdbcTemplate.update(DELETE_BY_AUTHOR_ID,
                new MapSqlParameterSource().addValue("authorId", id));
    }

    @Override
    public void deleteByBookId(int id) {
        jdbcTemplate.update(DELETE_BY_BOOK_ID,
                new MapSqlParameterSource().addValue("bookId", id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_FROM_AUTHOR_TO_BOOK, new MapSqlParameterSource());
    }

    private AuthorToBook bindAuthorToBook(ResultSet rs) throws SQLException {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(rs.getInt(AUTHOR_ID));
        authorToBook.setBookId(rs.getInt(BOOK_ID));
        return authorToBook;
    }
}
