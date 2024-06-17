package com.awbd.bookshopspringcloud.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "baskets")
public class Basket extends RepresentationModel<Basket> {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sent")
    private Boolean sent = false;

    @Column(name = "cost")
    private double cost = 0;

    @ManyToOne(targetEntity = User.class)
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "basket")
    @JsonManagedReference
    private List<BookBasket> baskets = null;

    public Basket() {
    }

    public Basket(int id, Boolean sent, double cost, User user) {
        this.id = id;
        this.sent = sent;
        this.cost = cost;
        this.user = user;
    }

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return id == basket.id && Double.compare(cost, basket.cost) == 0 && Objects.equals(sent, basket.sent) && Objects.equals(user, basket.user) && Objects.equals(baskets, basket.baskets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sent, cost, user, baskets);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", sent=" + sent +
                ", cost=" + cost +
                ", user=" + user +
                ", baskets=" + baskets +
                '}';
    }
}
