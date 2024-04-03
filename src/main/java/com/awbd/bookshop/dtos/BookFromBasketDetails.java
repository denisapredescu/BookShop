package com.awbd.bookshop.dtos;

import java.util.Objects;

public class BookFromBasketDetails {
    private String name;
    private double price;
    private int copies;
    private int id;

    public BookFromBasketDetails(String name, double price, int copies,int id) {
        this.name = name;
        this.price = price;
        this.copies = copies;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookFromBasketDetails that = (BookFromBasketDetails) o;
        return Double.compare(price, that.price) == 0 && copies == that.copies && id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, copies, id);
    }
}
