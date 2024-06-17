package com.awbd.bookshopspringcloud.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "book_baskets")
public class BookBasket extends RepresentationModel<BookBasket> {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "copies")
    @Min(value = 1)
    private int copies = 1;

    @Column(name = "price")
    private double price = 0;

    @ManyToOne(targetEntity = Book.class)
    @PrimaryKeyJoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @ManyToOne(targetEntity = Basket.class)
    @PrimaryKeyJoinColumn(name = "basket_id")
    @JsonBackReference
    private Basket basket;

    public BookBasket() {
    }

    public BookBasket(int id, int copies, double price, Book book, Basket basket) {
        this.id = id;
        this.copies = copies;
        this.price = price;
        this.book = book;
        this.basket = basket;
    }

    public BookBasket(int copies, double price, Book book, Basket basket) {
        this.copies = copies;
        this.price = price;
        this.book = book;
        this.basket = basket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
