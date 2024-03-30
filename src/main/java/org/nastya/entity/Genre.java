package org.nastya.entity;

public enum Genre {
    FANTASY("fantasy"),
    DETECTIVE("detective"),
    NOVEL("novel"),
    ADVENTURE("adventure"),
    PSYCHOLOGY("psychology"),
    HORROR("horror");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
