package org.nastya.dao.database;

import org.nastya.dao.BookDao;
import org.nastya.entity.Book;
import org.nastya.entity.Genre;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDatabaseDao implements BookDao {
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "vampyrrr9712";


    static final String ID = "id";
    static final String TITLE = "title";
    static final String PUBLISHING_YEAR = "publishingYear";
    static final String GENRE = "genre";

    private static final String REQUEST_BY_ID = "SELECT id, title, published, genre FROM books WHERE id = ?";
    private static final String SELECT = "SELECT id, title, published, genre FROM books";
    private static final String INSERT = "INSERT INTO books (title, published, genre) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE books SET title = ?, published = ?, genre = ? WHERE id = ?";
    private static final String DELETION_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String DELETE_FROM_AUTHORS = "DELETE FROM books";
    private static final String SELECT_BY_NAME = "SELECT * FROM books WHERE title=?";


    @Override
    public Book findById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(REQUEST_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return bindBook(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by id failed", e);
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT)) {
            while (rs.next()) {
                Book book = bindBook(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find all failed", e);
        }
        return bookList;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = bindBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by name failed", e);
        }
        return books;
    }

    @Override
    public int insert(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getPublishingYear());
            stmt.setString(3, book.getGenre().name());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
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
    public void save(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getPublishingYear());
            stmt.setString(3, book.getGenre().name());
            stmt.setInt(4, book.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Save failed", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(DELETION_BY_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete by id failed", e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DELETE_FROM_AUTHORS);
        } catch (SQLException e) {
            throw new RuntimeException("Delete all failed", e);
        }
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
