package org.nastya.dto;

import org.nastya.entity.Country;
import org.nastya.entity.Gender;

import java.time.LocalDate;

public class AuthorFormDTO {
    private int id;
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Country country;
    private Gender gender;
    private int age;

    @Override
    public String toString() {
        return "AuthorFormDTO{" +
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

    public void setAge(int age) {
        this.age = age;
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

    public Country getCountry() {
        return country;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public int getAge() {
        return age;
    }
}
