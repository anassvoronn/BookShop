package org.nastya.dto;

public class GenderOptionDTO {
    private final String value;
    private final String label;

    public GenderOptionDTO(String value, String label) {
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
