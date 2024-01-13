package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;
import org.nastya.service.exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorListItemDTO> findAll() {
        List<Author> authors = authorDao.findAll();
        log.info("Found '{}' authors", authors.size());
        List<AuthorListItemDTO> dtos = authorMapper.mapToAuthorListItemDTO(authors);
        for (AuthorListItemDTO authorDto : dtos) {
            LocalDate birthDate = authorDto.getBirthDate();
            LocalDate deathDate = authorDto.getDeathDate();
            int ages = calculateAge(birthDate, deathDate);
            authorDto.setAge(ages);
            log.info("Calculated age '{}' for author '{}' with id '{}'",
                    authorDto.getAge(), authorDto.getName(), authorDto.getId());
        }
        return dtos;
    }

    private int calculateAge(LocalDate dateOfBirth, LocalDate deathDate) {
        if (dateOfBirth == null) {
            log.info("Date of birth was not provided. Returning 0");
            return 0;
        }
        if (deathDate == null) {
            LocalDate now = LocalDate.now();
            log.info("Date of death was not provided. Using current date '{}'", now);
            return Period.between(dateOfBirth, now).getYears();
        }
        return Period.between(dateOfBirth, deathDate).getYears();
    }

    public AuthorFormDTO findById(int id) throws AuthorNotFoundException {
        Author author = authorDao.findById(id);
        if (author == null) {
            log.info("Author with id '{}' was not found", id);
            throw new AuthorNotFoundException("There is no author with this id " + id);
        }
        log.info("Found author '{}' with id '{}'", author.getName(), author.getId());
        AuthorFormDTO dto = authorMapper.mapToAuthorFormDTO(author);
        LocalDate birthDate = dto.getBirthDate();
        LocalDate deathDate = dto.getDeathDate();
        int ages = calculateAge(birthDate, deathDate);
        dto.setAge(ages);
        log.info("Calculated age '{}' for author '{}' with id '{}'",
                dto.getAge(), dto.getName(), dto.getId());
        return dto;
    }

    public void deleteAuthor(int authorId) throws AuthorNotFoundException{
        Author author = authorDao.findById(authorId);
        if (author == null) {
            log.info("Author with id '{}' was not found", authorId);
            throw new AuthorNotFoundException("There is no author with this id " + authorId);
        }
        authorDao.deleteById(authorId);
        log.info("Deleted author '{}' with id '{}'", author.getName(), author.getId());
    }
}
