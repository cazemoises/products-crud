package com.crud.model;

import java.time.LocalDate;
import java.time.LocalDate;

public class Customer {

    private String id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthdate;
    private LocalDate created_at;
    private LocalDate updated_at;
    private LocalDate deleted_at; // Added soft delete column

    public Customer() {
    }

    public Customer(String id, String name, String surname, String email, LocalDate birthdate, LocalDate created_at, LocalDate updated_at, LocalDate deleted_at) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public LocalDate getDeleted_at() {
        return deleted_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public void setDeleted_at(LocalDate deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}
