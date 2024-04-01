package com.awbd.bookshop.dtos;

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
}
