package com.awbd.bookshop.services.category;

import com.awbd.bookshop.exceptions.exceptions.DatabaseError;
import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private static final Integer CATEGORY_ID = 0;
    private static final Category CATEGORY = new Category(
            CATEGORY_ID,
            "category"
    );

    @InjectMocks
    private CategoryService categoryServiceUnderTest;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        when(categoryRepository.findByName(CATEGORY.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY);

        var result = categoryServiceUnderTest.save(CATEGORY);
        verify(categoryRepository, times(1)).findByName(CATEGORY.getName());
        verify(categoryRepository, times(1)).save(CATEGORY);

        assertEquals(CATEGORY, result);
    }

    @Test
    void save_category_is_null() {
        var result = categoryServiceUnderTest.save(null);
        verify(categoryRepository, never()).findByName(any());
        verify(categoryRepository, never()).save(any());

        assertNull(result);
    }

    @Test
    void save_already_in() {
        when(categoryRepository.findByName(CATEGORY.getName())).thenReturn(Optional.of(CATEGORY));

        var result = categoryServiceUnderTest.save(CATEGORY);
        verify(categoryRepository, times(1)).findByName(CATEGORY.getName());
        verify(categoryRepository, never()).save(CATEGORY);

        assertEquals(CATEGORY, result);
    }

    @Test
    void save_DatabaseError_at_findByName() {
        when(categoryRepository.findByName(CATEGORY.getName())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> categoryServiceUnderTest.save(CATEGORY));
        verify(categoryRepository, times(1)).findByName(CATEGORY.getName());
        verify(categoryRepository, never()).save(CATEGORY);
    }

    @Test
    void save_DatabaseError_at_save() {
        when(categoryRepository.findByName(CATEGORY.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(CATEGORY)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> categoryServiceUnderTest.save(CATEGORY));
        verify(categoryRepository, times(1)).findByName(CATEGORY.getName());
        verify(categoryRepository, times(1)).save(CATEGORY);
    }

    @Test
    void updateCategory() {
        when(categoryRepository.findById(eq(CATEGORY_ID))).thenReturn(Optional.of(CATEGORY));

        Category update_data = new Category(CATEGORY_ID, "update category");
        CATEGORY.setName(update_data.getName());

        when(categoryRepository.save(any())).thenReturn(CATEGORY);

        var result = categoryServiceUnderTest.updateCategory(update_data, CATEGORY_ID);
        verify(categoryRepository, times(1)).findById(CATEGORY_ID);
        verify(categoryRepository).save(CATEGORY);
        assertEquals(CATEGORY, result);
    }

    @Test
    void updateCategory_NoSuchElementException() {
        when(categoryRepository.findById(eq(CATEGORY_ID))).thenThrow(NoFoundElementException.class);

        assertThrows(NoFoundElementException.class, () -> categoryServiceUnderTest.updateCategory(any(), CATEGORY_ID));
        verify(categoryRepository, times(1)).findById(CATEGORY_ID);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_DatabaseError_at_findById() {
        when(categoryRepository.findById(eq(CATEGORY_ID))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> categoryServiceUnderTest.updateCategory(new Category(), CATEGORY_ID));
        verify(categoryRepository, times(1)).findById(CATEGORY_ID);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_DatabaseError_at_save() {
        when(categoryRepository.findById(eq(CATEGORY_ID))).thenReturn(Optional.of(CATEGORY));
        when(categoryRepository.save(any())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> categoryServiceUnderTest.updateCategory(new Category(), CATEGORY_ID));
        verify(categoryRepository, times(1)).findById(CATEGORY_ID);
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void deleteCategory() {
        categoryServiceUnderTest.deleteCategory(CATEGORY_ID);
        verify(categoryRepository).deleteById(CATEGORY_ID);
    }

    @Test
    void getCategories() {
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        var result = categoryServiceUnderTest.getCategories();
        assertEquals(categories, result);
        assertEquals(categories.size(), result.size());
    }

    @Test
    void getCategories_DatabaseError() {
        when(categoryRepository.findAll()).thenThrow(DatabaseError.class);
        assertThrows(DatabaseError.class, () -> categoryServiceUnderTest.getCategories());
    }
}