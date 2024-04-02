package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Test
    void getAuthors() {
        Author author1 = new Author();
        authorRepository.save(author1);

        Author author2 = new Author();
        authorRepository.save(author2);

        List<Author> authors = authorRepository.getAuthors();
        assertEquals(2, authors.size());
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
    }

    @Test
    void getAuthor() {
        Author author = new Author("firstname", "lastname", "nationality");
        authorRepository.save(author);

        Optional<Author> actualAuthor = authorRepository.getAuthor("firstname", "lastname");
        assertTrue(actualAuthor.isPresent());
        assertNotNull(actualAuthor.get());
        assertEquals(author, actualAuthor.get());
    }

    @Test
    void getAuthor_notFound() {
        Optional<Author> actualAuthor = authorRepository.getAuthor("firstname", "lastname");
        assertTrue(actualAuthor.isEmpty());
    }
}