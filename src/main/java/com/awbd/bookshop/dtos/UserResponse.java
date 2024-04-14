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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthority(String authority) {
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
