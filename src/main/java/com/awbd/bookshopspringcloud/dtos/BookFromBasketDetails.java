package com.awbd.bookshopspringcloud.dtos;

import java.util.Objects;

public class BookFromBasketDetails {
    private String name;
    private double price;
    private int copies;
    private int id;

    private String author_name;
    private String categories;

    public BookFromBasketDetails(String name, double price, int copies,int id,String author_name,String categories) {
        this.name = name;
        this.price = price;
        this.copies = copies;
        this.id = id;
        this.author_name = author_name;
        this.categories = categories;
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

    public String getAuthor_name() {
        return author_name;
    }

    public String getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookFromBasketDetails that = (BookFromBasketDetails) o;
        return Double.compare(price, that.price) == 0 && copies == that.copies && id == that.id && Objects.equals(name, that.name) && Objects.equals(author_name, that.author_name) && Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, copies, id, author_name, categories);
    }
}
