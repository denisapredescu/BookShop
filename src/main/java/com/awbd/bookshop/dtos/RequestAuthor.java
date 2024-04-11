package com.awbd.bookshop.dtos;

import jakarta.persistence.Column;

public class RequestAuthor {
    private String firstName;

    private String lastName;

    private String nationality;

    public RequestAuthor(String firstName, String lastName, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    public RequestAuthor() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }
}
