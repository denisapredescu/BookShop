package com.awbd.bookshop.dtos;

public class BookFromBasketDetails {
    private String name;
    private int price;
    private int copies;

    public BookFromBasketDetails(String name, int price, int copies) {
        this.name = name;
        this.price = price;
        this.copies = copies;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }
}
