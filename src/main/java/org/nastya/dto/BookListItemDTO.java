package org.nastya.dto;

import org.nastya.entity.Genre;

public class BookListItemDTO {
    private int id;
    private String title;
    private int publishingYear;
    private Genre genre;
    private int views;

    @Override
    public String toString() {
        return "BookListItemDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", published=" + publishingYear +
                ", genre='" + genre + '\'' +
                '}';
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
