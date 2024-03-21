package com.awbd.bookshop.model;
import jakarta.persistence.*;
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Column(name = "id",insertable=false, updatable=false)
    private int id;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")

    private User user;
    public Authority() {
    }

    public Authority(int id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Authority(int id, String authority) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}