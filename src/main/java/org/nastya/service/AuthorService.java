package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorDao authorDao;

    public List<Author> findAll() {
        List<Integer> age = authorDao.getAuthorsAge();
        List<Author> authors = authorDao.findAll();
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            author.setAge(age.get(i));
        }
        return authors;
    }

    public Author findById(int id) {
        return authorDao.findById(id);
    }
}
