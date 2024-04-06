package org.nastya.entity;

public enum Genre {
    FANTASY("Fantasy"),
    DETECTIVE("Detective"),
    NOVEL("Novel"),
    ADVENTURE("Adventure"),
    PSYCHOLOGY("Psychology"),
    HORROR("Horror");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
