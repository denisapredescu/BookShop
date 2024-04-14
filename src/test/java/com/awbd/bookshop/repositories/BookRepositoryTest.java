package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAvailableBooks() {
        // Mock author
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        // Mock book
        Book book1 = new Book("book1", 12);
        book1.setAuthor(author);

        Book book2 = new Book("book2", 20);
        book2.setAuthor(author);

        // Mock
        when(bookRepository.getAvailableBooks(any())).thenReturn(new PageImpl<>(List.of(book1, book2)));

        // Test
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(2, page.getTotalElements());
        assertTrue(page.getContent().contains(book1));
        assertTrue(page.getContent().contains(book2));

        // Verify
        verify(bookRepository).getAvailableBooks(any());
    }

    @Test
    void getAvailableBooks_notFound() {
        when(bookRepository.getAvailableBooks(any())).thenReturn(Page.empty());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Book> page = bookRepository.getAvailableBooks(pageable);

        assertEquals(0, page.getTotalElements());

        verify(bookRepository).getAvailableBooks(any());
    }

    @Test
    void getBooksByCategory() {
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
    void getBooksByCategory_notFound() {
        Category category = new Category("Fiction");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        List<Book> result = bookRepository.getBooksByCategory(category.getName());
        assertEquals(0, result.size());

        verify(bookRepository, times(1)).getBooksByCategory(category.getName());
    }

    @Test
    void getBooksByAuthor() {
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
    void getBooksByAuthor_notFound() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        List<Book> result = bookRepository.getBooksByAuthor(author.getFirstName(), author.getLastName());
        assertEquals(0, result.size());

        verify(bookRepository).getBooksByAuthor(author.getFirstName(), author.getLastName());
    }

    @Test
    void save() {
        Book book = new Book(1, "book", 12);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook);
        assertEquals(book, savedBook);

        verify(bookRepository).save(book);
    }

    @Test
    void findAll() {
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
    void findById() {
        Book book = new Book(1, "book", 12);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Optional<Book> result = bookRepository.findById(book.getId());
        assertTrue(result.isPresent());
        assertEquals(book, result.get());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void findById_notFound() {
        int nonExistentBookId = 999;
        when(bookRepository.findById(nonExistentBookId)).thenReturn(Optional.empty());

        Optional<Book> result = bookRepository.findById(nonExistentBookId);
        assertTrue(result.isEmpty());

        verify(bookRepository).findById(nonExistentBookId);
    }

    @Test
    void updateBook() {
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
