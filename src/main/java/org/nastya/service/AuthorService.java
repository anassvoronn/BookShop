package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorDao authorDao;

    public List<Author> findAll() {
        List<Author> authors = authorDao.findAll();
        for (Author author : authors) {
            LocalDate birthDate = author.getBirthDate();
            LocalDate deathDate = author.getDeathDate();
            int ages = calculateAge(birthDate, deathDate);
            author.setAge(ages);
        }
        return authors;
    }

    private int calculateAge(LocalDate dateOfBirth, LocalDate deathDate) {
        if (dateOfBirth != null) {
            if (deathDate != null) {
                return Period.between(dateOfBirth, deathDate).getYears();
            } else {
                return Period.between(dateOfBirth, LocalDate.now()).getYears();
            }
        } else {
            return 0;
        }
    }

    public Author findById(int id) {
        return authorDao.findById(id);
    }
}
