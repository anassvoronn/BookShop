package org.nastya.dto;

public class GenreOptionDTO {
    private final String value;
    private final String label;

    public GenreOptionDTO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
