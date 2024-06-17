package com.awbd.bookshopspringcloud.dtos;

public class BookResponse {
    private int id;

    private String name;

    private double price;

    private int year;

    private int volume;

    private String series_name;

    private String author_name;

    private String categories;

    public BookResponse(int id, String name, double price, int year, int volume, String series_name, String author_name, String categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
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

    public int getYear() {
        return year;
    }

    public int getVolume() {
        return volume;
    }

    public String getSeries_name() {
        return series_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getCategories() {
        return categories;
    }
}
