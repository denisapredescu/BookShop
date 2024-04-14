package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
class AuthorRepositoryH2Test {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void getAuthors() {
        List<Author> authors = authorRepository.getAuthors();
        assertNotNull(authors);

        log.info("getUsers...");
        authors.forEach(user -> log.info(user.getLastName()));
    }

    @Test
    void getAuthor() {
        Optional<Author> author = authorRepository.getAuthor("author2_firstname", "author2_lastname");
        assertTrue(author.isPresent());
        assertNotNull(author.get());

        log.info("getAuthor...");
        log.info(String.valueOf(author.get().getId()));
        log.info(author.get().getFirstName());
        log.info(author.get().getLastName());
        log.info(author.get().getNationality());
    }
}