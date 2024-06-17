package com.awbd.bookshopspringcloud.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @OneToMany(mappedBy = "authority")
    private List<User> users;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}