package com.awbd.bookshop.repositories;

import com.awbd.bookshop.exceptions.exceptions.DeletedBookException;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getAvailableBooks() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        Author savedAuthor = authorRepository.save(author);

        Book book1 = new Book("book1", 12);
        book1.setAuthor(savedAuthor);
        bookRepository.save(book1);

        Book book2 = new Book("book2", 20);
        book2.setAuthor(savedAuthor);
        bookRepository.save(book2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(8, page.getTotalElements());//2-D, 8-eu
        assertTrue(page.getContent().contains(book1));
        assertTrue(page.getContent().contains(book2));
    }

    @Test
    void getAvailableBooks_notFound() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(6, page.getTotalElements());//0-D, 6-M
    }

    @Test
    void getBooksByCategory() {
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book("book1", 12);
        book.setBookCategories(categories);
        bookRepository.save(book);

        List<Book> books = bookRepository.getBooksByCategory(category.getName());
        assertEquals(1, books.size());
        assertTrue(books.contains(book));
    }

    @Test
    void getBooksByCategory_notFound() {
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        Book book = new Book("book1", 12);
        bookRepository.save(book);

        List<Book> books = bookRepository.getBooksByCategory(category.getName());
        assertEquals(0, books.size());
    }

    @Test
    void testGetBooksByAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        authorRepository.save(author);

        Book book1 = new Book("book1", 12);
        book1.setAuthor(author);
        bookRepository.save(book1);

        Book book2 = new Book("book2", 12);
        book2.setAuthor(author);
        bookRepository.save(book2);

        List<Book> books = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());

        assertEquals(2, books.size());
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }

    @Test
    void testGetBooksByAuthor_notFound() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        Book book1 = new Book("book1", 12);
        bookRepository.save(book1);

        Book book2 = new Book("book2", 12);
        bookRepository.save(book2);

        List<Book> books = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
        assertEquals(0, books.size());
    }

    @Test
    void save() {
        Book book = new Book(1, "book", 12);
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);

        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
        assertTrue(actualBookOptional.isPresent());
        Book actualBook = actualBookOptional.get();
        assertNotNull(actualBook);
        assertEquals(savedBook, actualBook);
    }

    @Test
    void findAll() {
        Book book1 = new Book("book1", 12);
        bookRepository.save(book1);

        Book book2 = new Book("book2", 12);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertEquals(9, books.size());//2-D, eu -9
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }

    @Test
    void findAll_notFound() {
        List<Book> books = bookRepository.findAll();
        assertEquals(7, books.size());//0-D,7-m
    }

    @Test
    void findById() {
        Book book = new Book(1, "book", 12);
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);

        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
        assertTrue(actualBookOptional.isPresent());

        Book actualBook = actualBookOptional.get();
        assertNotNull(actualBook);
        assertEquals(savedBook, actualBook);
    }

    @Test
    void findById_not_found() {
        int nonExistentBookId = 999;

        Optional<Book> actualBookOptional = bookRepository.findById(nonExistentBookId);
        assertTrue(actualBookOptional.isEmpty());
    }

    @Test
    void updateBook() {
        Book book = new Book(1, "book", 12);
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);

        savedBook.setName("updated book");
        savedBook.setPrice(30);
        bookRepository.save(savedBook);

        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
        assertTrue(actualBookOptional.isPresent());
        book = actualBookOptional.get();
        assertNotNull(book);
        assertEquals(savedBook.getName(), book.getName());
        assertEquals(savedBook.getPrice(), book.getPrice());
    }
}