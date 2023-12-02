package org.nastya.service;

import org.nastya.dto.AuthorListItemDTO;
import org.nastya.entity.Author;

import java.util.List;

public interface AuthorMapper {
    AuthorListItemDTO map(Author author);

    List<AuthorListItemDTO> map(List<Author> authors);
}