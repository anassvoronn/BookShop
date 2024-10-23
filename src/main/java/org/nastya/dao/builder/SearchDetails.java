package org.nastya.dao.builder;

import org.nastya.entity.Genre;

public class SearchDetails {
    private final Genre genre;
    private final String title;
    private final String publishingYear;
    private final Integer authorId;

    protected SearchDetails(Genre genre, String title, String publishingYear, Integer authorId) {
        this.genre = genre;
        this.title = title;
        this.publishingYear = publishingYear;
        this.authorId = authorId;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public Integer getAuthorId() {
        return authorId;
    }
}
