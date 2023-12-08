package org.nastya.service.mapper;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;
import org.nastya.service.AuthorMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorListItemDTO mapToAuthorListItemDTO(Author author) {
        AuthorListItemDTO dto = new AuthorListItemDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirthDate());
        dto.setDeathDate(author.getDeathDate());
        dto.setGender(author.getGender());
        dto.setCountry(author.getCountry());
        return dto;
    }

    @Override
    public List<AuthorListItemDTO> mapToAuthorListItemDTO(List<Author> authors) {
        List<AuthorListItemDTO> dtos = new ArrayList<>();
        for (Author author : authors) {
            AuthorListItemDTO dto = mapToAuthorListItemDTO(author);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public AuthorFormDTO mapToAuthorFormDTO(Author author) {
        AuthorFormDTO dto = new AuthorFormDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirthDate());
        dto.setDeathDate(author.getDeathDate());
        dto.setGender(author.getGender());
        dto.setCountry(author.getCountry());
        return dto;
    }
}
