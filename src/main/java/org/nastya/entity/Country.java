package org.nastya.entity;

public enum Country {
     RUSSIA("Russia"),
     JAPAN("Japan"),
     ENGLAND("England"),
     GEORGIA("Georgia"),
     USA("Usa"),
     SPAIN("Spain"),
     UKRAINE("Ukraine"),
     LITHUANIA("Lithuania"),
     CANADA("Canada"),
     CHINA("China");

     private final String label;

     Country(String label) {
          this.label = label;
     }

     public String getLabel() {
          return label;
     }
}
