package com.awbd.bookshopspringcloud.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RequestBook {

    @NotNull
    @NotEmpty(message = "The name cannot be blank!")
    private String name;
    @Min(value = 0,message = "Price should have minimum value zero")
    @NotNull(message = "The price must be set!")
    @Positive(message = "Price should be greater than zero")
    private double price;
    @Min(value = 0,message = "Year should be greater than zero")
    private int year;
    @Min(value = 0,message = "Volume should be greater than zero")
    private int volume = 0;

    private String series_name = null;

    @NotNull
    @NotEmpty(message = "The name cannot be blank!")
    private String author_name;

    @NotNull
    @NotEmpty(message = "The category cannot be blank!")
    private String categories;

    public RequestBook(String name, double price, int year, int volume, String series_name, String author_name, String categories) {
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
        this.author_name = author_name;
        this.categories = categories;
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
