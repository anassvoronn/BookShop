package org.nastya.entity;

import java.time.LocalDate;

public class Author {

    private int id;
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Country country;
    private Gender gender;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                ", country='" + country + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateAsString() {
        return String.valueOf(birthDate);
    }

    public Country getCountry() {
        return country;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }
}