package org.nastya.dao;

import org.nastya.entity.Book;

import java.util.List;

public interface BookDao {

    Book findById(int id);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    List<Book> findByTitleContaining(String title);

    int insert(Book book);

    void save(Book book);

    void deleteById(int id);

    void deleteAll();
}
