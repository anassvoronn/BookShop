package org.nastya.dao.builder;

import org.nastya.entity.Genre;

public class SearchDetails {
    private final Genre genre;
    private final String title;
    private final String publishingYear;
    private final Integer authorId;

    private SearchDetails(Genre genre, String title, String publishingYear, Integer authorId) {
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

    public static class Builder {
        private Genre genre;
        private String title;
        private String publishingYear;
        private Integer authorId;

        public Builder setGenre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPublishingYear(String publishingYear) {
            this.publishingYear = publishingYear;
            return this;
        }

        public Builder setAuthorId(Integer authorId) {
            this.authorId = authorId;
            return this;
        }

        public SearchDetails build() {
            return new SearchDetails(genre, title, publishingYear, authorId);
        }
    }
}
