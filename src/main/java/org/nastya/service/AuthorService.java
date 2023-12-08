package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
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
    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorListItemDTO> findAll() {
        List<Author> authors = authorDao.findAll();
        List<AuthorListItemDTO> dtos = authorMapper.mapToAuthorListItemDTO(authors);
        for (AuthorListItemDTO authorDto : dtos) {
            LocalDate birthDate = authorDto.getBirthDate();
            LocalDate deathDate = authorDto.getDeathDate();
            int ages = calculateAge(birthDate, deathDate);
            authorDto.setAge(ages);
        }
        return dtos;
    }

    private int calculateAge(LocalDate dateOfBirth, LocalDate deathDate) {
        if (dateOfBirth == null) {
            return 0;
        }
        if (deathDate == null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return Period.between(dateOfBirth, deathDate).getYears();
    }

    public AuthorFormDTO findById(int id) {
        Author author = authorDao.findById(id);
        AuthorFormDTO dto = authorMapper.mapToAuthorFormDTO(author);
        LocalDate birthDate = dto.getBirthDate();
        LocalDate deathDate = dto.getDeathDate();
        int ages = calculateAge(birthDate, deathDate);
        dto.setAge(ages);
        return dto;
    }
}
