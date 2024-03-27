package com.awbd.bookshop.dtos;

public class UserResponse {
    Integer id;
    String email;
    String authority;

    public UserResponse(Integer id, String email, String authority) {
        this.id = id;
        this.email = email;
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthority() {
        return authority;
    }
}
