package com.awbd.bookshop.services.category;

import com.awbd.bookshop.models.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(String token, Category newCategory);
    Category updateCategory(String token, Category updateCategory, int id);
    void deleteCategory(String token, int id);
    List<Category> getCategories();

    Category save(Category newCategory);
}
