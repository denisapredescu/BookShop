package com.awbd.bookshop.services.category;

import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category addCategory(Category newCategory) {
        return save(newCategory);
    }

    @Transactional
    @Override
    public Category save(Category newCategory) {
        if (newCategory == null)
            return null;

        Category category = categoryRepository.findByName(newCategory.getName()).orElse(null);

        if(category == null) {
            return categoryRepository.save(newCategory);
        }

        return category;
    }

    @Transactional
    @Override
    public Category updateCategory(Category updateCategory, int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category with this id not found"));

        category.setName(updateCategory.getName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category with this id not found"));
        return category;
    }
    @Override
    public List<Category> getCategoriesByIds(List<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }
}
