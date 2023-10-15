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

    static final String ID = "id";
    static final String NAME = "name";
    static final String DATE_OF_BIRTH = "date_of_birth";
    static final String GENDER = "gender";
    static final String COUNTRY = "country";

    static final String SELECT = "SELECT id, name, date_of_birth, gender, country FROM authors";
    static final String REQUEST_BY_ID = "SELECT id, name, date_of_birth, gender, country FROM authors WHERE id = ?";
    static final String INSERT = "INSERT INTO authors (name, date_of_birth, gender, country) VALUES (?, ?, ?, ?)";
    static final String DELETION_BY_ID = "DELETE FROM authors WHERE id = ?";
    static final String DELETE_FROM_AUTHORS = "DELETE FROM authors";
    static final String UPDATE = "UPDATE authors SET name = ?, date_of_birth = ?, gender = ?, country = ? WHERE id = ?";
    static final String SELECT_BY_NAME = "SELECT * FROM authors WHERE name=?";
    static final String SELECT_BY_GENDER = "SELECT * FROM authors WHERE gender=?";
    static final String SELECT_BY_GENDER_AND_BIRTH_DATE = "SELECT * FROM authors WHERE gender=? AND date_of_birth = ?";
    static final String DELETE_BY_GENDER = "DELETE FROM authors WHERE gender = ?;";
    static final String SELECT_BY_GENDER_OR_COUNTRY = "SELECT * FROM authors WHERE gender = ? OR country = ?";

    @Override
    public Author findById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(REQUEST_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return bindAuthor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by id failed", e);
        }
        return null;
    }

    @Override
    public List<Author> findByName(String name) {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Author author = bindAuthor(rs);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by name failed", e);
        }
        return authors;
    }

    @Override
    public List<Author> findByGender(String gender) {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_GENDER)) {
            stmt.setString(1, gender);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Author author = bindAuthor(rs);
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by gender failed", e);
        }
        return authors;
    }

    @Override
    public List<Author> findByGenderAndByBirthDate(String gender, String birthDate) {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_GENDER_AND_BIRTH_DATE)) {
            stmt.setString(1, gender);
            stmt.setDate(2, Date.valueOf(birthDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Author author = bindAuthor(rs);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by gender and birth date failed", e);
        }
        return authors;
    }

    @Override
    public List<Author> findByGenderOrByCountry(String gender, String country) {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_GENDER_OR_COUNTRY)) {
            stmt.setString(1, gender);
            stmt.setString(2, country);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Author author = bindAuthor(rs);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find by gender or country failed", e);
        }
        return authors;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authorList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT)) {
            while (rs.next()) {
                Author author = bindAuthor(rs);
                authorList.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find all failed", e);
        }
        return authorList;
    }

    private Author bindAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt(ID));
        author.setName(rs.getString(NAME));
        LocalDate date = rs.getDate(DATE_OF_BIRTH).toLocalDate();
        author.setBirthDate(date);
        author.setGender(rs.getString(GENDER));
        author.setCountry(rs.getString(COUNTRY));
        return author;
    }

    @Override
    public int insert(Author author) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
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
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, String.valueOf(author.getBirthDate()));
            stmt.setString(3, author.getGender());
            stmt.setString(4, author.getCountry());
            stmt.setInt(5, author.getId());
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
    public void deleteAllByGender(String gender) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_GENDER)) {
            stmt.setString(1, gender);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete all by gender failed", e);
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
}