package org.nastya.dao;

import org.nastya.entity.Author;

import java.util.List;

public interface AuthorDao {

    Author findById(int id);

    List<Author> findByName(String name);

    List<Author> findByGender(String gender);

    List<Author> findByGenderAndByBirthDate(String gender, String birthDate);

    List<Author> findByGenderOrByCountry(String gender, String country);

    List<Author> findAll();

    int insert(Author author);

    void save(Author author);

    void deleteById(int id);

    void deleteAllByGender(String gender);

    void deleteAll();

    int getBiggestId();

    int getCountByGender(String gender);

    int getCount();
}