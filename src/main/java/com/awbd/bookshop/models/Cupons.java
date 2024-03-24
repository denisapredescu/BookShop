package com.awbd.bookshop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cupons")
public class Cupons {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "discount")
    private Double discount;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Cupons(Integer id, Double discount) {
        this.id = id;
        this.discount = discount;
    }

    public Cupons(Integer id, Double discount, User user) {
        this.id = id;
        this.discount = discount;
        this.user = user;
    }

    public Cupons() {

    }
}
