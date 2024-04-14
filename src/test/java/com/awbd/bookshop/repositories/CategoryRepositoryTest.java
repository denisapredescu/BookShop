package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class CategoryRepositoryTest {

    @Mock
    CategoryRepository categoryRepository;

    @Test
    void findByName() {
        // Create a category
        Category category = new Category("category");

        // Mock
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        // Call the findByName method
        Optional<Category> actualCategory = categoryRepository.findByName(category.getName());

        // Assertions
        assertTrue(actualCategory.isPresent());
        assertNotNull(actualCategory.get());
        assertEquals(category, actualCategory.get());
    }

    @Test
    void findByName_notfound() {
        // Mock
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Create a category
        Category category = new Category("category");

        // Call the findByName method
        Optional<Category> actualCategory = categoryRepository.findByName(category.getName());

        // Assertion
        assertTrue(actualCategory.isEmpty());
    }
}
