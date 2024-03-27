package com.awbd.bookshop.dtos;

public class BookFromBasketDetails {
    private String name;
    private double price;
    private int copies;

    public BookFromBasketDetails(String name, double price, int copies) {
        this.name = name;
        this.price = price;
        this.copies = copies;
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
}
