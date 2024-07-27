package org.nastya.dao.database;

import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;
import org.nastya.entity.Country;
import org.nastya.entity.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthorDatabaseDao implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String ID = "id";
    static final String NAME = "name";
    static final String DATE_OF_BIRTH = "date_of_birth";
    static final String DATE_OF_DEATH = "death_date";
    static final String GENDER = "gender";
    static final String COUNTRY = "country";
    static final String AGE = "age";

    private static final String SELECT = "SELECT id, name, date_of_birth, death_date, gender, country FROM authors";
    private static final String REQUEST_BY_ID = "SELECT id, name, date_of_birth, death_date, gender, country FROM authors WHERE id = :id";
    private static final String INSERT = "INSERT INTO authors (name, date_of_birth, death_date, gender, country) VALUES (:name, :birthDate, :deathDate, :gender, :country) RETURNING id";
    private static final String DELETION_BY_ID = "DELETE FROM authors WHERE id = :id";
    private static final String DELETE_FROM_AUTHORS = "DELETE FROM authors";
    private static final String UPDATE = "UPDATE authors SET name = :name, date_of_birth = :birthDate, death_date = :deathDate, gender = :gender, country = :country WHERE id = :id";
    private static final String SELECT_BY_NAME = "SELECT * FROM authors WHERE name= :name";
    private static final String SELECT_BY_GENDER = "SELECT * FROM authors WHERE gender= :gender";
    private static final String SELECT_BY_GENDER_AND_BIRTH_DATE = "SELECT * FROM authors WHERE gender= :gender AND date_of_birth = :birthDate";
    private static final String DELETE_BY_GENDER = "DELETE FROM authors WHERE gender = :gender";
    private static final String SELECT_BY_GENDER_OR_COUNTRY = "SELECT * FROM authors WHERE gender = :gender OR country = :country";
    private static final String COUNT_BY_GENDER = "SELECT COUNT(*) FROM authors WHERE gender = :gender";
    private static final String GET_BIGGEST_ID = "SELECT MAX(id) FROM authors";
    private static final String COUNT_FROM_AUTHORS = "SELECT COUNT(*) FROM authors";

    public AuthorDatabaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author findById(int id) {
        try {
            return jdbcTemplate.queryForObject(REQUEST_BY_ID,
                    new MapSqlParameterSource().addValue("id", id),
                    (rs, rowNum) -> bindAuthor(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME,
                new MapSqlParameterSource().addValue("name", name),
                (rs, rowNum) -> bindAuthor(rs));
    }

    @Override
    public List<Author> findByGender(Gender gender) {
        return jdbcTemplate.query(SELECT_BY_GENDER,
                new MapSqlParameterSource().addValue("gender", gender.name()),
                (rs, rowNum) -> bindAuthor(rs));
    }

    @Override
    public List<Author> findByGenderAndByBirthDate(Gender gender, String birthDate) {
        return jdbcTemplate.query(SELECT_BY_GENDER_AND_BIRTH_DATE,
                new MapSqlParameterSource()
                        .addValue("gender", gender.name())
                        .addValue("birthDate", Date.valueOf(birthDate)),
                (rs, rowNum) -> bindAuthor(rs));
    }

    @Override
    public List<Author> findByGenderOrByCountry(Gender gender, Country country) {
        return jdbcTemplate.query(SELECT_BY_GENDER_OR_COUNTRY,
                new MapSqlParameterSource()
                        .addValue("gender", gender.name())
                        .addValue("country", country.name()),
                (rs, rowNum) -> bindAuthor(rs));
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query(SELECT,
                (rs, rowNum) -> bindAuthor(rs));
    }

    private Author bindAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt(ID));
        author.setName(rs.getString(NAME));
        Date birthDate = rs.getDate(DATE_OF_BIRTH);
        if (birthDate != null) {
            LocalDate date = birthDate.toLocalDate();
            author.setBirthDate(date);
        }
        Date deathDate = rs.getDate(DATE_OF_DEATH);
        if (deathDate != null) {
            LocalDate dateOfDeath = deathDate.toLocalDate();
            author.setDeathDate(dateOfDeath);
        }
        author.setGender(Gender.valueOf(rs.getString(GENDER)));
        author.setCountry(Country.valueOf(rs.getString(COUNTRY)));
        return author;
    }

    @Override
    public int insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT,
                new MapSqlParameterSource()
                        .addValue("name", author.getName())
                        .addValue("birthDate", author.getBirthDate())
                        .addValue("deathDate", author.getDeathDate())
                        .addValue("gender", author.getGender().name())
                        .addValue("country", author.getCountry().name()), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void save(Author author) {
        jdbcTemplate.update(UPDATE,
                new MapSqlParameterSource()
                        .addValue("name", author.getName())
                        .addValue("birthDate", Date.valueOf(author.getBirthDate()))
                        .addValue("deathDate", Date.valueOf(author.getDeathDate()))
                        .addValue("gender", author.getGender().name())
                        .addValue("country", author.getCountry().name())
                        .addValue("id", author.getId()));
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETION_BY_ID,
                new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public void deleteAllByGender(Gender gender) {
        jdbcTemplate.update(DELETE_BY_GENDER,
                new MapSqlParameterSource().addValue("gender", gender.name()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_FROM_AUTHORS, new MapSqlParameterSource());
    }

    @Override
    public int getBiggestId() {
        Integer biggestId = jdbcTemplate.queryForObject(GET_BIGGEST_ID, new HashMap<>(), Integer.class);
        return Objects.requireNonNullElse(biggestId, -1);
    }

    @Override
    public int getCountByGender(Gender gender) {
        Integer count = jdbcTemplate.queryForObject(COUNT_BY_GENDER,
                new MapSqlParameterSource().addValue("gender", gender.name()), Integer.class);
        return Objects.requireNonNullElse(count, 0);
    }

    @Override
    public int getCount() {
        Integer count = jdbcTemplate.queryForObject(COUNT_FROM_AUTHORS, new MapSqlParameterSource(), Integer.class);
        return Objects.requireNonNullElse(count, 0);
    }
}