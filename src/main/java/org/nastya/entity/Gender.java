package org.nastya.entity;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    UNKNOWN("Unknown");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
