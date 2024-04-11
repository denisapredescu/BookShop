package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
public class EntityManagerTest {

//    @Autowired
//    private EntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testGetAvailableBooks() {
        // Insert test data into the H2 database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        Book book1 = new Book("book1", 12);
        book1.setAuthor(author);

        Book book2 = new Book("book2", 20);
        book2.setAuthor(author);

        entityManager.persist(author);
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();
        entityManager.clear(); // Clear the persistence context

        // Call the repository method being tested
        Page<Book> page = bookRepository.getAvailableBooks(PageRequest.of(0, 10, Sort.by("name")));

        // Perform assertions on the results
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertTrue(page.getContent().contains(book1));
        Assertions.assertTrue(page.getContent().contains(book2));
    }

    @Test
    public void findProduct() {
        System.out.println(entityManager.getEntityManagerFactory());
//        Product productFound = entityManager.find(Product.class, 1L);
//        assertEquals(productFound.getCode(), "PCEZ");
    }
}