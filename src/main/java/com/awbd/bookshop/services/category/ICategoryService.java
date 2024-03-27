package com.awbd.bookshop.services.category;

import com.awbd.bookshop.models.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category newCategory);
    Category updateCategory(Category updateCategory, int id);
    void deleteCategory(int id);
    List<Category> getCategories();
    Category save(Category newCategory);
}
