package com.awbd.bookshopspringcloud.dtos;


public class UserDetails {
    private Integer id;
    private String firstName = "";
    private String lastName = "";
    private String username;
    private String email;

    private String password;

    public UserDetails() {
    }

    public UserDetails(Integer id, String firstName, String lastName, String username, String email,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
