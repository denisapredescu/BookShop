package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByName() {
        Category category = new Category("category");
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> actualCategory = categoryRepository.findByName(savedCategory.getName());
        assertTrue(actualCategory.isPresent());
        assertNotNull(actualCategory.get());
        assertEquals(savedCategory, actualCategory.get());
    }

    @Test
    void findByName_notfound() {
        Category category = new Category("category");

        Optional<Category> actualCategory = categoryRepository.findByName(category.getName());
        assertTrue(actualCategory.isEmpty());
    }
}