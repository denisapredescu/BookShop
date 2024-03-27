package com.awbd.bookshop.dtos;

public class UpdateUser {
    String firstName;
    String lastName;
    String username;

    public UpdateUser() {
    }

    public UpdateUser(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }
}
