package org.nastya.dao;

import org.nastya.entity.Author;

import java.util.List;

public interface AuthorDao {

    Author findById(int id);

    List<Author> findAll();

    int insert(Author author);

    void save(Author author);

    void deleteById(int id);

    void deleteAll();
}