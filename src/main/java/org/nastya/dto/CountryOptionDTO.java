package org.nastya.dto;

public class CountryOptionDTO {
    private final String value;
    private final String label;

    public CountryOptionDTO(String value, String label) {
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
