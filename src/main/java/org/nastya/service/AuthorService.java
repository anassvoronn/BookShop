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
        return authorDao.findAll();
    }
}
