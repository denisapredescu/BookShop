package com.awbd.bookshopspringcloud.dtos;


public class UserDetails {
    private Integer id;
    private String firstName = "";
    private String lastName = "";
    private String username;
    private String email;

    public UserDetails() {
    }

    public UserDetails(Integer id, String firstName, String lastName, String username, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public Integer getId() {
        return id;
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

    public String getEmail() {
        return email;
    }
}
