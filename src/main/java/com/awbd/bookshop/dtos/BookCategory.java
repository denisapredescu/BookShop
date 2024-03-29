package com.awbd.bookshop.dtos;

import com.awbd.bookshop.models.Category;

import java.util.List;

public class BookCategory {
    private int id;
    private List<Category> categories;

    public BookCategory(int id, List<Category> categories) {
        this.id = id;
        this.categories = categories;
    }

    public BookCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
