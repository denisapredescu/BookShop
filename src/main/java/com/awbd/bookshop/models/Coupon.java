package com.awbd.bookshop.models;

import jakarta.persistence.*;

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

    public Double getDiscount() {
        return discount;
    }
}
