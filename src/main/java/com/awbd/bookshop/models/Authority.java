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

//    @ManyToOne
//    @JoinColumn(name = "id", referencedColumnName = "id")
//    private User user;

//    @OneToMany(targetEntity = User.class)
//    @PrimaryKeyJoinColumn(name = "user_id")
//    private Set<User> users;

    public Authority() {
    }

    public Authority(int id, String authority, Set<User> users) {
        this.id = id;
        this.authority = authority;
       // this.users = users;
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

//    public Set<User> getUsers() {
//        return users;
//    }

//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    //    public Authority(int id, String authority, User user) {
//        this.id = id;
//        this.authority = authority;
//        this.user = user;
//    }
//
//    public Authority(String authority, User user) {
//        this.authority = authority;
//        this.user = user;
//    }
//
//    public Authority(int id, String authority) {
//        this.id = id;
//        this.authority = authority;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getAuthority() {
//        return authority;
//    }
//
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}