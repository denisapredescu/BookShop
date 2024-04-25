package com.awbd.bookshop.dtos;

import jakarta.persistence.Column;
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

    public RequestBook(String name, double price, int year, int volume, String series_name) {
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
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
}
