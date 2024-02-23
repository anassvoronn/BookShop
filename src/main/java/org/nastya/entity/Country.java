package org.nastya.entity;

public enum Country {
     RUSSIA("Russia"),
     ENGLAND("England"),
     USA("Usa"),
     UKRAINE("Ukraine"),
     CANADA("Canada");

     private final String label;

     Country(String label) {
          this.label = label;
     }

     public String getLabel() {
          return label;
     }
}
