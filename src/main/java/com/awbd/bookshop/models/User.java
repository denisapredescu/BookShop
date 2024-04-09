package com.awbd.bookshop.models;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

//    @OneToMany(mappedBy = "user")
//    List<Authority> authority;

    @ManyToOne(targetEntity = Authority.class)
    @PrimaryKeyJoinColumn(name = "authority_id")
    private Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int id, String username, String email, String password, String firstName, String lastName, boolean enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public User(String username, String email, String password, String firstName, String lastName, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public User(int id, String username, String email, String password, String firstName, String lastName, boolean enabled, Authority authority) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.authority = authority;
    }
}
