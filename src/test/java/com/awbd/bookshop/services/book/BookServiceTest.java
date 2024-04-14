package com.awbd.bookshop.services.book;

import com.awbd.bookshop.exceptions.exceptions.DatabaseError;
import com.awbd.bookshop.exceptions.exceptions.DeletedBookException;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.BookRepository;
import com.awbd.bookshop.services.author.IAuthorService;
import com.awbd.bookshop.services.category.ICategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final Integer BOOK_ID = 0;
    private static  final Book BOOK = new Book(
            BOOK_ID,
            "book",
            20,
            2000,
            1,
            "Series",
            false
    );
    private static final Author AUTHOR = new Author(
            0,
            "firstName",
            "lastName",
            "nationality"
    );
    private static final List<Category> CATEGORIES = List.of(
            new Category(),
            new Category()
    );

    @InjectMocks
    private BookService bookServiceUnderTest;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ICategoryService categoryService;
    @Mock
    private IAuthorService authorService;

    @Test
    void addBook() {
        when(bookRepository.save(BOOK)).thenReturn(BOOK);

        var result = bookServiceUnderTest.addBook(BOOK);
        verify(bookRepository, times(1)).save(BOOK);

        assertEquals(BOOK, result);
    }

    @Test
    void addBook_DatabaseError() {
        when(bookRepository.save(BOOK)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addBook(BOOK));
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void addAuthorToBook() {
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        when(authorService.save(AUTHOR)).thenReturn(AUTHOR);

        BOOK.setAuthor(AUTHOR);

        when(bookRepository.save(BOOK)).thenReturn(BOOK);

        var result = bookServiceUnderTest.addAuthorToBook(BOOK_ID, AUTHOR);
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(authorService, times(1)).save(AUTHOR);
        assertEquals(BOOK.getAuthor(), AUTHOR);
        verify(bookRepository, times(1)).save(BOOK);
        assertEquals(BOOK, result);
    }

    @Test
    void addAuthorToBook_NoSuchElementException() {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> bookServiceUnderTest.addAuthorToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(authorService, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void addAuthorToBook_DatabaseError_at_findById(){
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addAuthorToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(authorService, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void addAuthorToBook_DeletedBookException() {
        BOOK.setIs_deleted(true);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        assertThrows(DeletedBookException.class, () ->  bookServiceUnderTest.addAuthorToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(authorService, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void addAuthorToBook_DatabaseError_at_save_author() {
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        when(authorService.save(AUTHOR)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addAuthorToBook(BOOK_ID, AUTHOR));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(authorService, times(1)).save(AUTHOR);
        verify(bookRepository, never()).save(BOOK);
    }

    @Test
    void addAuthorToBook_DatabaseError_at_update_book() {
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        when(authorService.save(AUTHOR)).thenReturn(AUTHOR);

        BOOK.setAuthor(AUTHOR);

        when(bookRepository.save(BOOK)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addAuthorToBook(BOOK_ID, AUTHOR));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(authorService, times(1)).save(AUTHOR);
        assertEquals(BOOK.getAuthor(), AUTHOR);
        verify(bookRepository, times(1)).save(BOOK);
    }

    @Test
    void addCategoriesToBook() {
        BOOK.setBookCategories(new ArrayList<>());
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        AtomicInteger categoryIdCounter = new AtomicInteger(1);
        when(categoryService.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(categoryIdCounter.getAndIncrement());
            return category;
        });

        when(bookRepository.save(BOOK)).thenReturn(BOOK);

        var result = bookServiceUnderTest.addCategoriesToBook(BOOK_ID, CATEGORIES);
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(categoryService, times(CATEGORIES.size())).save(any(Category.class));
        assertEquals(new HashSet<>(BOOK.getBookCategories()), new HashSet<>(CATEGORIES));
        verify(bookRepository, times(1)).save(BOOK);
        assertEquals(BOOK, result);
        assertEquals(BOOK.getBookCategories().size(), CATEGORIES.size());
    }

    @Test
    void addCategoriesToBook_NoSuchElementException() {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> bookServiceUnderTest.addCategoriesToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(categoryService, never()).save(any(Category.class));
        verify(bookRepository, never()).save(BOOK);
    }

    @Test
    void addCategoriesToBook_DatabaseError_at_findById() {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addCategoriesToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(categoryService, never()).save(any(Category.class));
        verify(bookRepository, never()).save(BOOK);
    }

    @Test
    void addCategoriesToBook_DeletedBookException() {
        BOOK.setIs_deleted(true);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        assertThrows(DeletedBookException.class, () ->  bookServiceUnderTest.addCategoriesToBook(BOOK_ID, any()));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(categoryService, never()).save(any(Category.class));
        verify(bookRepository, never()).save(BOOK);
    }

    @Test
    void addCategoriesToBook_DatabaseError_at_save_category() {
        BOOK.setBookCategories(new ArrayList<>());
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        when(categoryService.save(any(Category.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addCategoriesToBook(BOOK_ID, CATEGORIES));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(categoryService, times(1)).save(any(Category.class));
        verify(bookRepository, never()).save(BOOK);
    }

    @Test
    void addCategoriesToBook_DatabaseError_at_update_book() {
        BOOK.setBookCategories(new ArrayList<>());
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        AtomicInteger categoryIdCounter = new AtomicInteger(1);
        when(categoryService.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(categoryIdCounter.getAndIncrement());
            return category;
        });

        when(bookRepository.save(BOOK)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.addCategoriesToBook(BOOK_ID, CATEGORIES));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK.getIs_deleted(), false);
        verify(categoryService, times(CATEGORIES.size())).save(any(Category.class));
        assertEquals(BOOK.getBookCategories(), CATEGORIES);
        verify(bookRepository, times(1)).save(BOOK);
    }

    @Test
    void updateBook() {
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        Book update_data = new Book(
                BOOK_ID,
                "update book",
                30,
                2000,
                0,
                null,
                false
        );
        BOOK.setName(update_data.getName());
        BOOK.setPrice(update_data.getPrice());
        BOOK.setSeries_name(update_data.getSeries_name());
        BOOK.setVolume(update_data.getVolume());
        BOOK.setYear(update_data.getYear());

        when(bookRepository.save(BOOK)).thenReturn(BOOK);

        var result = bookServiceUnderTest.updateBook(update_data, eq(BOOK_ID));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository).save(BOOK);

        assertEquals(BOOK, result);
    }

    @Test
    void updateBook_NoSuchElementException() {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> bookServiceUnderTest.updateBook(new Book(), eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void updateBook_DatabaseError_at_findById() {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.updateBook(new Book(), eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void updateBook_DeletedBookException() {
        BOOK.setIs_deleted(true);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        assertThrows(DeletedBookException.class, () -> bookServiceUnderTest.updateBook(new Book(), eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void updateBook_DatabaseError_at_update_book() {
        BOOK.setIs_deleted(false);

        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));
        when(bookRepository.save(any())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.updateBook(new Book(), eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void deleteBook() {
        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        BOOK.setIs_deleted(true);

        when(bookRepository.save(BOOK)).thenReturn(BOOK);

        bookServiceUnderTest.deleteBook(eq(BOOK_ID));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, times(1)).save(BOOK);
    }

    @Test
    void deleteBook_NoSuchElementException()  {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> bookServiceUnderTest.deleteBook(eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBook_DatabaseError_at_findById()  {
        when(bookRepository.findById(eq(BOOK_ID))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.deleteBook(eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBook_DatabaseError_at_update_book() {
        when(bookRepository.findById(eq(BOOK_ID))).thenReturn(Optional.of(BOOK));

        BOOK.setIs_deleted(true);

        when(bookRepository.save(BOOK)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.deleteBook(eq(BOOK_ID)));
        verify(bookRepository, times(1)).findById(BOOK_ID);
        verify(bookRepository, times(1)).save(BOOK);
    }

    @Test
    void getBooks() {
        List<Book> books = List.of(new Book(), new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        var result = bookServiceUnderTest.getBooks();
        assertEquals(books, result);
        assertEquals(books.size(), result.size());
    }

    @Test
    void getBooks_DatabaseError() {
        when(bookRepository.findAll()).thenThrow(DatabaseError.class);
        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.getBooks());
    }

    @Test
    public void getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        // Mocking Pageable object for the first page with 10 results per page
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<Book> mockPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.getAvailableBooks(pageable)).thenReturn(mockPage);

        List<Book> result = bookServiceUnderTest.getAvailableBooks(0, 10);
        assertEquals(books, result);
    }

    @Test
    void getAvailableBooks_DatabaseError() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        // Mocking Pageable object for the first page with 10 results per page
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        // Mocking Page object with mockBooks as content
        Page<Book> page = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.getAvailableBooks(pageable)).thenThrow(DatabaseError.class);
        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.getAvailableBooks(0, 10));
    }

    @Test
    void getBooksByAuthor() {
        BOOK.setAuthor(AUTHOR);
        List<Book> books = List.of(BOOK, BOOK);

        when(bookRepository.getBooksByAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(books);

        var result = bookServiceUnderTest.getBooksByAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
        verify(bookRepository, times(1)).getBooksByAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
        assertEquals(books, result);
    }

    @Test
    void getBooksByAuthor_DatabaseError() {
        when(bookRepository.getBooksByAuthor(any(), any())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.getBooksByAuthor(any(), any()));
        verify(bookRepository, times(1)).getBooksByAuthor(any(), any());
    }

    @Test
    void getBooksByCategory() {
        List<Book> books = List.of(BOOK, BOOK);

        when(bookRepository.getBooksByCategory(any())).thenReturn(books);

        var result = bookServiceUnderTest.getBooksByCategory(any());
        verify(bookRepository, times(1)).getBooksByCategory(any());
        assertEquals(books, result);
    }

    @Test
    void getBooksByCategory_DatabaseError() {
        when(bookRepository.getBooksByCategory(any())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookServiceUnderTest.getBooksByCategory(any()));
        verify(bookRepository, times(1)).getBooksByCategory(any());
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(BOOK));

        var result = bookServiceUnderTest.getBookById(BOOK_ID);
        verify(bookRepository, times(1)).findById(BOOK_ID);
        assertEquals(BOOK, result);
    }

    @Test
    void getBookById_NoSuchElementException() {
        when(bookRepository.findById(BOOK_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () ->  bookServiceUnderTest.getBookById(BOOK_ID));
        verify(bookRepository, times(1)).findById(BOOK_ID);
    }

    @Test
    void getBookById_DatabaseError() {
        when(bookRepository.findById(BOOK_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  bookServiceUnderTest.getBookById(BOOK_ID));
        verify(bookRepository, times(1)).findById(BOOK_ID);
    }
}