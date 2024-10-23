package org.nastya.dao.builder;

import org.nastya.entity.Genre;

public class SearchDetailsBuilder {
    private Genre genre;
    private String title;
    private String publishingYear;
    private Integer authorId;

    public SearchDetailsBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public SearchDetailsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SearchDetailsBuilder setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
        return this;
    }

    public SearchDetailsBuilder setAuthorId(Integer authorId) {
        this.authorId = authorId;
        return this;
    }

    public SearchDetails build() {
        return new SearchDetails(genre, title, publishingYear, authorId);
    }
}
