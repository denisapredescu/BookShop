package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class AuthorRepositoryTest {

    @Mock
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAuthors() {
        // Mocking
        when(authorRepository.findAll()).thenReturn(List.of(
                new Author("John", "Doe", "American"),
                new Author("Jane", "Doe", "British")
        ));

        // Calling the method to be tested
        List<Author> authors = authorRepository.findAll();

        // Assertions
        assertEquals(2, authors.size());
        assertEquals("John", authors.get(0).getFirstName());
        assertEquals("Doe", authors.get(0).getLastName());
        assertEquals("American", authors.get(0).getNationality());
        assertEquals("Jane", authors.get(1).getFirstName());
        assertEquals("Doe", authors.get(1).getLastName());
        assertEquals("British", authors.get(1).getNationality());

        // Verifying
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthor() {
        // Mock
        when(authorRepository.getAuthor(anyString(), anyString())).thenReturn(Optional.of(
                new Author("John", "Doe", "American")
        ));

        // Calling the method to be tested
        Optional<Author> author = authorRepository.getAuthor("John", "Doe");

        // Assertions
        assertEquals("John", author.get().getFirstName());
        assertEquals("Doe", author.get().getLastName());
        assertEquals("American", author.get().getNationality());

        // Verifying
        verify(authorRepository, times(1)).getAuthor("John", "Doe");
    }

    @Test
    void getAuthor_notFound() {
        // Mock
        when(authorRepository.getAuthor(anyString(), anyString())).thenReturn(Optional.empty());

        // Calling the method to be tested
        Optional<Author> author = authorRepository.getAuthor("John", "Doe");

        // Assertions
        assertFalse(author.isPresent());

        // Verifying
        verify(authorRepository, times(1)).getAuthor("John", "Doe");
    }
}