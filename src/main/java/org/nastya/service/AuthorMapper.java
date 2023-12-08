package org.nastya.service;

import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;

import java.util.List;

public interface AuthorMapper {
    AuthorListItemDTO mapToAuthorListItemDTO(Author author);

    List<AuthorListItemDTO> mapToAuthorListItemDTO(List<Author> authors);

    AuthorFormDTO mapToAuthorFormDTO(Author author);
}