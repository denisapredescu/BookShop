package com.awbd.bookshop.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cupons")
public class Coupon {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "discount")
    private Double discount;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Coupon(Integer id, Double discount, User user) {
        this.id = id;
        this.discount = discount;
        this.user = user;
    }

    public Coupon() {

    }

    public Coupon(Double discount, User user) {
        this.discount = discount;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id) && Objects.equals(discount, coupon.discount) && Objects.equals(user, coupon.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discount, user);
    }
}
