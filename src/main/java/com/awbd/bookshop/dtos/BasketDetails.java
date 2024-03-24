package com.awbd.bookshop.dtos;


import java.util.List;
import java.util.Objects;

public class BasketDetails {
    private int id;
    private String sent;
    private int userId;
    private String email;
    private int cost;
    private List<BookFromBasketDetails> books;

    public BasketDetails(int id, String sent, int userId, String email, int cost, List<BookFromBasketDetails> books) {
        this.id = id;
        this.sent = sent;
        this.userId = userId;
        this.email = email;
        this.cost = cost;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getSent() {
        return sent;
    }

    public String getEmail() {
        return email;
    }

    public int getCost() {
        return cost;
    }

    public List<BookFromBasketDetails> getBooks() {
        return books;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketDetails that = (BasketDetails) o;
        return id == that.id && userId == that.userId && cost == that.cost && Objects.equals(sent, that.sent) && Objects.equals(email, that.email) && Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sent, userId, email, cost, books);
    }

    @Override
    public String toString() {
        return "BasketDetails{" +
                "id=" + id +
                ", sent='" + sent + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", cost=" + cost +
                ", books=" + books +
                '}';
    }
}