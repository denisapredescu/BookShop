package com.awbd.bookshop.models;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Column(name = "id",insertable=false, updatable=false)
    private int id;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    public Authority() {
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
}