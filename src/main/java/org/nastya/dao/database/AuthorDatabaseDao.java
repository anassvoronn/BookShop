package org.nastya.dao.database;

import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDatabaseDao implements AuthorDao {
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "vampyrrr9712";
    static final String QUERY = "SELECT id, name, date_of_birth, gender, country FROM authors";

    @Override
    public Author findById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name, date_of_birth, gender, country FROM authors WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    LocalDate localDate = LocalDate.parse("date_of_birth");
                    author.setBirthDate(localDate);
                    author.setGender(rs.getString("gender"));
                    author.setCountry(rs.getString("country"));
                    return author;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by id failed", e);
        }
        return null;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authorList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                LocalDate localDate = LocalDate.parse("date_of_birth");
                author.setBirthDate(localDate);
                author.setGender(rs.getString("gender"));
                author.setCountry(rs.getString("country"));
                authorList.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find all failed", e);
        }
        return authorList;
    }

    @Override
    public int insert(Author author) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO authors (name, date_of_birth, gender, country) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, author.getName());
            LocalDate date = author.getBirthDate();
            stmt.setDate(2, Date.valueOf(date));
            stmt.setString(3, author.getGender());
            stmt.setString(4, author.getCountry());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
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
    public void save(Author author) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM authors");
        } catch (SQLException e) {
            throw new RuntimeException("Delete all failed", e);
        }
    }
}