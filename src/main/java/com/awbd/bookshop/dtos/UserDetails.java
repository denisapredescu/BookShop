package com.awbd.bookshop.dtos;


public class UserDetails {
    private Integer id;
    private String firstName = "";
    private String lastName = "";
    private String birthday;
    private String email;
    private String role;

    public UserDetails() {
    }

    public UserDetails(Integer id, String firstName, String lastName, String birthday, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.role = role;
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

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
