package com.awbd.bookshop.dtos;

import com.awbd.bookshop.models.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class BookResponse {
    private int id;

    private String name;

    private int price;

    private int year;

    private int volume;

    private String series_name;

    private String author;

    private List<Category> categories;

    public BookResponse(int id, String name, int price, int year, int volume, String series_name, String author, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
        this.author = author;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
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

    public String getAuthor() {
        return author;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
