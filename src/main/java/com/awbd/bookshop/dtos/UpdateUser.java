package com.awbd.bookshop.dtos;

public class UpdateUser {
    String firstName;
    String lastName;

    public UpdateUser() {
    }

    public UpdateUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
