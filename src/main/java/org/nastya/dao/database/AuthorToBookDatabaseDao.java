package org.nastya.dao.database;

import org.nastya.ConnectionFactory.DatabaseConnectionFactory;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorToBookDatabaseDao implements AuthorToBookDao {
    private final DatabaseConnectionFactory connectionFactory;

    static final String AUTHOR_ID = "authorId";
    static final String BOOK_ID = "bookId";

    private static final String SELECT_AUTHOR_ID = "SELECT * FROM author_To_Book WHERE authorId = ?";
    private static final String SELECT_BOOK_ID = "SELECT * FROM author_To_Book WHERE bookId = ?";
    private static final String INSERT = "INSERT INTO author_To_Book (authorId, bookId) VALUES (?, ?)";
    private static final String DELETE_FROM_AUTHOR_TO_BOOK = "DELETE FROM author_To_Book";
    private static final String DELETE_BY_BOOK_ID = "DELETE FROM author_To_Book WHERE bookId = ?";
    private static final String DELETE_BY_AUTHOR_ID = "DELETE FROM author_To_Book WHERE authorId = ?";

    public AuthorToBookDatabaseDao(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<AuthorToBook> findByAuthorId(int id) {
        List<AuthorToBook> connections = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_AUTHOR_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AuthorToBook authorToBook = bindAuthorToBook(rs);
                connections.add(authorToBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by authorId failed", e);
        }
        return connections;
    }

    @Override
    public List<AuthorToBook> findByBookId(int id) {
        List<AuthorToBook> connections = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BOOK_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AuthorToBook authorToBook = bindAuthorToBook(rs);
                connections.add(authorToBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by authorToBooks failed", e);
        }
        return connections;
    }

    @Override
    public int insert(AuthorToBook authorToBook) {
        try (Connection conn = connectionFactory.getConnection();
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
                throw new SQLException("Inserting authorToBook failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed", e);
        }
    }

    @Override
    public void deleteByAuthorId(int id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_AUTHOR_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete by authorId failed", e);
        }
    }

    @Override
    public void deleteByBookId(int id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_BOOK_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete by bookId failed", e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DELETE_FROM_AUTHOR_TO_BOOK);
        } catch (SQLException e) {
            throw new RuntimeException("Delete all failed", e);
        }
    }

    private AuthorToBook bindAuthorToBook(ResultSet rs) throws SQLException {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(rs.getInt(AUTHOR_ID));
        authorToBook.setBookId(rs.getInt(BOOK_ID));
        return authorToBook;
    }
}
