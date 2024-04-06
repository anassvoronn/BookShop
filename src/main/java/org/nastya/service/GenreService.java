package org.nastya.service;

import org.nastya.dto.GenreOptionDTO;
import org.nastya.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    public List<GenreOptionDTO> getAllGenres() {
        List<GenreOptionDTO> genres = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            genres.add(new GenreOptionDTO(genre.name(), genre.getLabel()));
        }
        return genres;
    }
}
