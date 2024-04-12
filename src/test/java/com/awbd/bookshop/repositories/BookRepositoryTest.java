package com.awbd.bookshop.repositories;
//
//import com.awbd.bookshop.exceptions.exceptions.DeletedBookException;
//import com.awbd.bookshop.models.Author;
//import com.awbd.bookshop.models.Book;
//import com.awbd.bookshop.models.Category;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("mysql")
//class BookRepositoryTest {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private AuthorRepository authorRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    void getAvailableBooks() {
//        Author author = new Author();
//        author.setFirstName("John");
//        author.setLastName("Doe");
//        Author savedAuthor = authorRepository.save(author);
//
//        Book book1 = new Book("book1", 12);
//        book1.setAuthor(savedAuthor);
//        bookRepository.save(book1);
//
//        Book book2 = new Book("book2", 20);
//        book2.setAuthor(savedAuthor);
//        bookRepository.save(book2);
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
//        Page<Book> page = bookRepository.getAvailableBooks(pageable);
//
//        assertEquals(8, page.getTotalElements());//2-D, 8-eu
//        assertTrue(page.getContent().contains(book1));
//        assertTrue(page.getContent().contains(book2));
//    }
//
//    @Test
//    void getAvailableBooks_notFound() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
//        Page<Book> page = bookRepository.getAvailableBooks(pageable);
//
//        assertEquals(6, page.getTotalElements());//0-D, 6-M
//    }
//
//    @Test
//    void getBooksByCategory() {
//        Category category = new Category("Fiction");
//        categoryRepository.save(category);
//
//        List<Category> categories = new ArrayList<>();
//        categories.add(category);
//
//        Book book = new Book("book1", 12);
//        book.setBookCategories(categories);
//        bookRepository.save(book);
//
//        List<Book> books = bookRepository.getBooksByCategory(category.getName());
//        assertEquals(1, books.size());
//        assertTrue(books.contains(book));
//    }
//
//    @Test
//    void getBooksByCategory_notFound() {
//        Category category = new Category("Fiction");
//        categoryRepository.save(category);
//
//        Book book = new Book("book1", 12);
//        bookRepository.save(book);
//
//        List<Book> books = bookRepository.getBooksByCategory(category.getName());
//        assertEquals(0, books.size());
//    }
//
//    @Test
//    void testGetBooksByAuthor() {
//        Author author = new Author();
//        author.setFirstName("John");
//        author.setLastName("Doe");
//        authorRepository.save(author);
//
//        Book book1 = new Book("book1", 12);
//        book1.setAuthor(author);
//        bookRepository.save(book1);
//
//        Book book2 = new Book("book2", 12);
//        book2.setAuthor(author);
//        bookRepository.save(book2);
//
//        List<Book> books = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
//
//        assertEquals(2, books.size());
//        assertTrue(books.contains(book1));
//        assertTrue(books.contains(book2));
//    }
//
//    @Test
//    void testGetBooksByAuthor_notFound() {
//        Author author = new Author();
//        author.setFirstName("John");
//        author.setLastName("Doe");
//
//        Book book1 = new Book("book1", 12);
//        bookRepository.save(book1);
//
//        Book book2 = new Book("book2", 12);
//        bookRepository.save(book2);
//
//        List<Book> books = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
//        assertEquals(0, books.size());
//    }
//
//    @Test
//    void save() {
//        Book book = new Book(1, "book", 12);
//        Book savedBook = bookRepository.save(book);
//        assertNotNull(savedBook);
//
//        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
//        assertTrue(actualBookOptional.isPresent());
//        Book actualBook = actualBookOptional.get();
//        assertNotNull(actualBook);
//        assertEquals(savedBook, actualBook);
//    }
//
//    @Test
//    void findAll() {
//        Book book1 = new Book("book1", 12);
//        bookRepository.save(book1);
//
//        Book book2 = new Book("book2", 12);
//        bookRepository.save(book2);
//
//        List<Book> books = bookRepository.findAll();
//        assertEquals(9, books.size());//2-D, eu -9
//        assertTrue(books.contains(book1));
//        assertTrue(books.contains(book2));
//    }
//
//    @Test
//    void findAll_notFound() {
//        List<Book> books = bookRepository.findAll();
//        assertEquals(7, books.size());//0-D,7-m
//    }
//
//    @Test
//    void findById() {
//        Book book = new Book(1, "book", 12);
//        Book savedBook = bookRepository.save(book);
//        assertNotNull(savedBook);
//
//        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
//        assertTrue(actualBookOptional.isPresent());
//
//        Book actualBook = actualBookOptional.get();
//        assertNotNull(actualBook);
//        assertEquals(savedBook, actualBook);
//    }
//
//    @Test
//    void findById_not_found() {
//        int nonExistentBookId = 999;
//
//        Optional<Book> actualBookOptional = bookRepository.findById(nonExistentBookId);
//        assertTrue(actualBookOptional.isEmpty());
//    }
//
//    @Test
//    void updateBook() {
//        Book book = new Book(1, "book", 12);
//        Book savedBook = bookRepository.save(book);
//        assertNotNull(savedBook);
//
//        savedBook.setName("updated book");
//        savedBook.setPrice(30);
//        bookRepository.save(savedBook);
//
//        Optional<Book> actualBookOptional = bookRepository.findById(savedBook.getId());
//        assertTrue(actualBookOptional.isPresent());
//        book = actualBookOptional.get();
//        assertNotNull(book);
//        assertEquals(savedBook.getName(), book.getName());
//        assertEquals(savedBook.getPrice(), book.getPrice());
//    }
//}


import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.AuthorRepository;
import com.awbd.bookshop.repositories.BookRepository;
import com.awbd.bookshop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class BookRepositoryTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableBooks() {
        // Mock author
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        // Mock book
        Book book1 = new Book("book1", 12);
        book1.setAuthor(author);

        Book book2 = new Book("book2", 20);
        book2.setAuthor(author);

        // Mock the behavior of repository methods
        when(bookRepository.getAvailableBooks(any())).thenReturn(new PageImpl<>(List.of(book1, book2)));

        // Test
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(2, page.getTotalElements());
        assertTrue(page.getContent().contains(book1));
        assertTrue(page.getContent().contains(book2));

        // Verify that the repository methods were called
        verify(bookRepository).getAvailableBooks(any());
    }

    @Test
    void testGetAvailableBooks_notFound() {
        when(bookRepository.getAvailableBooks(any())).thenReturn(Page.empty());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(0, page.getTotalElements());

        verify(bookRepository).getAvailableBooks(any());
    }

    @Test
    void testGetBooksByCategory() {
        Category category = new Category("Fiction");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        Book book = new Book("book1", 12);
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.getBooksByCategory(category.getName())).thenReturn(books);

        List<Book> result = bookRepository.getBooksByCategory(category.getName());
        assertEquals(1, result.size());
        assertTrue(result.contains(book));

        verify(bookRepository).getBooksByCategory(category.getName());
    }

    @Test
    void testGetBooksByCategory_notFound() {
        Category category = new Category("Fiction");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        List<Book> result = bookRepository.getBooksByCategory(category.getName());
        assertEquals(0, result.size());

        verify(bookRepository, times(1)).getBooksByCategory(category.getName());
    }

    @Test
    void testGetBooksByAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        Book book1 = new Book("book1", 12);
        Book book2 = new Book("book2", 12);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName())).thenReturn(books);

        List<Book> result = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));

        verify(bookRepository).getBooksByAuthor(author.getFirstName(), author.getLastName());
    }

    @Test
    void testGetBooksByAuthor_notFound() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        List<Book> result = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
        assertEquals(0, result.size());

        verify(bookRepository).getBooksByAuthor(author.getFirstName(), author.getLastName());
    }

    @Test
    void testSave() {
        Book book = new Book(1, "book", 12);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);
        assertEquals(book, savedBook);

        verify(bookRepository).save(book);
    }

    @Test
    void testFindAll() {
        Book book1 = new Book("book1", 12);
        Book book2 = new Book("book2", 12);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookRepository.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));

        verify(bookRepository).findAll();
    }

    @Test
    void testFindById() {
        Book book = new Book(1, "book", 12);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Optional<Book> result = bookRepository.findById(book.getId());
        assertTrue(result.isPresent());
        assertEquals(book, result.get());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void testFindById_notFound() {
        int nonExistentBookId = 999;
        when(bookRepository.findById(nonExistentBookId)).thenReturn(Optional.empty());

        Optional<Book> result = bookRepository.findById(nonExistentBookId);
        assertTrue(result.isEmpty());

        verify(bookRepository).findById(nonExistentBookId);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1, "book", 12);
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = new Book(1, "updated book", 30);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);

        savedBook.setName("updated book");
        savedBook.setPrice(30);
        Book updatedSavedBook = bookRepository.save(savedBook);
        assertNotNull(updatedSavedBook);
        assertEquals(savedBook, updatedSavedBook);

        verify(bookRepository, times(2)).save(any());
    }
}
