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

//    @OneToOne(targetEntity = User.class)
//    @PrimaryKeyJoinColumn(name = "user_id")
//    private User user;


    public Cupons(Integer id, Double discount) {
        this.id = id;
        this.discount = discount;
    }

    public Cupons() {

    }
}
