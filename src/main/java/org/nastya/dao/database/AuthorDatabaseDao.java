package org.nastya.dao.database;

import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;

import java.sql.*;
import java.util.List;

public class AuthorDatabaseDao implements AuthorDao {
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "vampyrrr9712";
    static final String QUERY = "SELECT id, name, date_of_birth, gender, country FROM authors";

    @Override
    public Author findById(int id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setBirthDate(rs.getString("date_of_birth"));
                author.setGender(rs.getString("gender"));
                author.setCountry(rs.getString("country"));

            }
        } catch (SQLException e) {
            throw new RuntimeException("Find all failed", e);
        }
        return null;
    }

    @Override
    public int insert(Author author) {
        return 0;
    }

    @Override
    public void save(Author author) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
