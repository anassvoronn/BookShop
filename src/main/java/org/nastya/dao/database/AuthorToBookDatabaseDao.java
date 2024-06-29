package org.nastya.dao.database;

import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorToBookDatabaseDao implements AuthorToBookDao {
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "vampyrrr9712";

    static final String AUTHOR_ID = "authorId";
    static final String BOOK_ID = "bookId";

    private static final String SELECT_BOOK_ID = "SELECT bookId FROM authorToBook WHERE authorId = ?";
    private static final String SELECT_AUTHOR_ID = "SELECT authorId FROM authorToBook WHERE bookId = ?";
    private static final String INSERT = "INSERT INTO authorToBook (authorId, bookId) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE authorToBook SET authorId = ?, bookId = ?, WHERE authorId = ?";
    private static final String DELETE_FROM_AUTHOR_TO_BOOK = "DELETE FROM authorToBook";
    private static final String DELETE_BY_BOOK_ID = "DELETE FROM authorToBook WHERE bookId = ?";
    private static final String DELETE_BY_AUTHOR_ID = "DELETE FROM authorToBook WHERE authorId = ?";


    @Override
    public List<AuthorToBook> findByAuthorId(int id) {
        List<AuthorToBook> authorId = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_AUTHOR_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AuthorToBook authorToBook = bindAuthorToBook(rs);
                authorId.add(authorToBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by authorId failed", e);
        }
        return authorId;
    }

    @Override
    public List<AuthorToBook> findByBookId(int id) {
        List<AuthorToBook> bookId = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BOOK_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AuthorToBook authorToBook = bindAuthorToBook(rs);
                bookId.add(authorToBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by bookId failed", e);
        }
        return bookId;
    }

    @Override
    public int insert(AuthorToBook authorToBook) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, authorToBook.getAuthorId());
            stmt.setInt(2, authorToBook.getBookId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating authorToBook failed, no rows affected.");
            }
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting author failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed", e);
        }
    }

    @Override
    public void save(AuthorToBook authorToBook) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setInt(1, authorToBook.getAuthorId());
            stmt.setInt(2, authorToBook.getBookId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Save failed", e);
        }
    }

    @Override
    public void deleteByAuthorId(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_AUTHOR_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete by authorId failed", e);
        }
    }

    @Override
    public void deleteByBookId(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_BOOK_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete by bookId failed", e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DELETE_FROM_AUTHOR_TO_BOOK);
        } catch (SQLException e) {
            throw new RuntimeException("Delete all failed", e);
        }
    }

    private AuthorToBook bindAuthorToBook(ResultSet rs) throws SQLException{
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(rs.getInt(AUTHOR_ID));
        authorToBook.setBookId(rs.getInt(BOOK_ID));
        return authorToBook;
    }
}
