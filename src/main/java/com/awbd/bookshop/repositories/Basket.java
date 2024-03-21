package com.awbd.bookshop.repositories;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sent")
    private Boolean sent = false;

    @Column(name = "cost")
    private int cost = 0;

//    @ManyToOne
//    @PrimaryKeyJoinColumn(name = "user_id")
//    private User user;

    public Basket() {
    }

//    public Basket(int id, Boolean sent, int cost, User user) {
//        this.id = id;
//        this.sent = sent;
//        this.cost = cost;
//        this.user = user;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

//    @Override
//    public String toString() {
//        return "Basket{" +
//                "id=" + id +
//                ", sent=" + sent +
//                ", cost=" + cost +
//                ", user=" + user +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Basket basket = (Basket) o;
//        return id == basket.id && cost == basket.cost && Objects.equals(sent, basket.sent) && Objects.equals(user, basket.user);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, sent, cost, user);
//    }


}
