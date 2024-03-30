package org.nastya.dto;

import org.nastya.entity.Genre;

public class BookListItemDTO {
    private int id;
    private String title;
    private int published;
    private Genre genre;

    @Override
    public String toString() {
        return "BookListItemDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", published=" + published +
                ", genre='" + genre + '\'' +
                '}';
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

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
