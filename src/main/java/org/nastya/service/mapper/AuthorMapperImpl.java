package org.nastya.service.mapper;

import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;
import org.nastya.service.AuthorMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorListItemDTO map(Author author) {
        AuthorListItemDTO dto = new AuthorListItemDTO();
        dto.setId(author.getId());
        dto.setBirthDate(author.getBirthDate());
        dto.setDeathDate(author.getDeathDate());
        dto.setCountry(author.getCountry());
        return dto;
    }

    @Override
    public List<AuthorListItemDTO> map(List<Author> authors) {
        List<AuthorListItemDTO> dtos = new ArrayList<>();
        for (Author author : authors) {
            AuthorListItemDTO dto = map(author);
            dtos.add(dto);
        }
        return dtos;
    }
}
