package org.nastya.dao;

import org.nastya.entity.AuthorToBook;

import java.util.List;

public interface AuthorToBookDao {

    List<AuthorToBook> findByAuthorId(int id);

    List<AuthorToBook> findByBookId(int id);

    int insert(AuthorToBook authorToBook);

    void save(AuthorToBook authorToBook);

    void deleteByAuthorId(int id);

    void deleteByBookId(int id);

    void deleteAll();
}
