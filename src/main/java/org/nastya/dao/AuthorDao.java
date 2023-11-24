package org.nastya.dao;

import org.nastya.entity.Author;
import org.nastya.entity.Country;
import org.nastya.entity.Gender;

import java.util.List;

public interface AuthorDao {

    Author findById(int id);

    List<Author> findByName(String name);

    List<Author> findByGender(Gender gender);

    List<Author> findByGenderAndByBirthDate(Gender gender, String birthDate);

    List<Author> findByGenderOrByCountry(Gender gender, Country country);

    List<Author> findAll();

    int insert(Author author);

    void save(Author author);

    void deleteById(int id);

    void deleteAllByGender(Gender gender);

    void deleteAll();

    int getBiggestId();

    int getCountByGender(Gender gender);

    int getCount();

    List<Integer> getAuthorsAge();
}