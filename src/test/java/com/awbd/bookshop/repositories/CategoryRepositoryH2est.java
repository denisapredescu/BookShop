package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
class CategoryRepositoryH2est {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByName() {
        Optional<Category> category = categoryRepository.findByName("fiction");
        assertTrue(category.isPresent());
        assertNotNull(category.get());

        log.info("findByName - fiction ...");
        log.info("Category id: " + category.get().getId());
        log.info("Category name: " + category.get().getName());
    }

    @Test
    void findByName_notfound() {
        Optional<Category> category = categoryRepository.findByName("non-existing category");
        assertTrue(category.isEmpty());

        log.info("findByName_notfound - non-existing category ...");
        log.info("Category not found: " + category.isEmpty());
    }
}
