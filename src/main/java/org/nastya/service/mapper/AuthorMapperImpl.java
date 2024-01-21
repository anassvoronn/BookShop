package org.nastya.service.mapper;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;
import org.nastya.service.AuthorMapper;
import org.nastya.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapperImpl implements AuthorMapper {
    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    @Override
    public AuthorListItemDTO mapToAuthorListItemDTO(Author author) {
        log.info("Mapping author '{}' with name '{}' to AuthorListItemDTO", author.getId(), author.getName());
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
        log.info("Mapping '{}' authors to AuthorListItemDTO", authors.size());
        List<AuthorListItemDTO> dtos = new ArrayList<>();
        for (Author author : authors) {
            AuthorListItemDTO dto = mapToAuthorListItemDTO(author);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public AuthorFormDTO mapToAuthorFormDTO(Author author) {
        log.info("Mapping author '{}' with name '{}' to AuthorFormDTO", author.getId(), author.getName());
        AuthorFormDTO dto = new AuthorFormDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirthDate());
        dto.setDeathDate(author.getDeathDate());
        dto.setGender(author.getGender());
        dto.setCountry(author.getCountry());
        return dto;
    }

    @Override
    public Author mapToAuthor(AuthorFormDTO authorFormDTO) {
        Author authorEntity = new Author();
        authorEntity.setId(authorFormDTO.getId());
        authorEntity.setName(authorFormDTO.getName());
        authorEntity.setBirthDate(authorFormDTO.getBirthDate());
        authorEntity.setDeathDate(authorFormDTO.getDeathDate());
        authorEntity.setGender(authorFormDTO.getGender());
        authorEntity.setCountry(authorFormDTO.getCountry());
        return authorEntity;
    }
}
