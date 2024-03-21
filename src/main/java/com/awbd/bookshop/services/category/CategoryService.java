package com.awbd.bookshop.services.category;

import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category addCategory(String token, Category newCategory) {
//        JwtUtil.verifyAdmin(token);
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
    public Category updateCategory(String token, Category updateCategory, int id) {
//        JwtUtil.verifyAdmin(token);
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category with this id not found"));

        category.setName(updateCategory.getName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String token, int id) {
//        JwtUtil.verifyAdmin(token);
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
