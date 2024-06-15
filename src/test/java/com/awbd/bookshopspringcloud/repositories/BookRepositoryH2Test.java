//package com.awbd.bookshop.repositories;
//
//import com.awbd.bookshop.models.Book;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.*;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles("h2")
//@Slf4j
//class BookRepositoryH2Test {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Test
//    void getAvailableBooks() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
//        Page<Book> page = bookRepository.getAvailableBooks(pageable);
//        assertNotNull(page.getContent());
//
//        log.info("getAvailableBooks...");
//        for (Book book: page.getContent()) {
//            log.info("Book id: " + book.getId());
//            log.info("Book name: " + book.getName());
//            log.info("Book total price: " + book.getPrice());
//        }
//    }
//
//    @Test
//    void getBooksByCategory() {
//        List<Book> books = bookRepository.getBooksByCategory("romantic");
//        assertNotNull(books);
//
//        log.info("getBooksByCategory - romantic: ");
//        for (Book book: books) {
//            log.info("Book id: " + book.getId());
//            log.info("Book name: " + book.getName());
//            log.info("Book total price: " + book.getPrice());
//        }
//    }
//
//    @Test
//    void getBooksByCategory_notFound() {
//        List<Book> books = bookRepository.getBooksByCategory("fiction");
//        assertEquals(0, books.size());
//
//        log.info("getBooksByCategory_notFound - fiction ...");
//        log.info("Books not found: " + books.isEmpty());
//    }
//
//    @Test
//    void getBooksByAuthor() {
//        List<Book> books = bookRepository.getBooksByAuthor("author1_firstname", "author1_lastname");
//        assertNotNull(books);
//
//        log.info("getBooksByAuthor...");
//        for (Book book: books) {
//            log.info("Book id: " + book.getId());
//            log.info("Book name: " + book.getName());
//            log.info("Book total price: " + book.getPrice());
//        }
//    }
//
//    @Test
//    void getBooksByAuthor_notFound() {
//        List<Book> books = bookRepository.getBooksByAuthor("non-existing firstname", "non-existing lastname");
//        assertEquals(0, books.size());
//
//        log.info("getBooksByAuthor_notFound...");
//        log.info("Books not found: " + books.isEmpty());
//    }
//
//    @Test
//    void findAll() {
//        List<Book> books = bookRepository.findAll();
//
//        log.info("getBooksByAuthor...");
//        for (Book book: books) {
//            log.info("Book id: " + book.getId());
//            log.info("Book name: " + book.getName());
//            log.info("Book total price: " + book.getPrice());
//        }
//    }
//
//    @Test
//    void findById() {
//        Optional<Book> book = bookRepository.findById(1);
//        assertTrue(book.isPresent());
//
//        log.info("findById...");
//        log.info("Book id: " + book.get().getId());
//        log.info("Book name: " + book.get().getName());
//        log.info("Book total price: " + book.get().getPrice());
//    }
//
//    @Test
//    void findById_notFound() {
//        int nonExistentBookId = 999;
//        Optional<Book> book = bookRepository.findById(nonExistentBookId);
//        assertTrue(book.isEmpty());
//
//        log.info("findById_notFound...");
//        log.info("Book found: " + book.isPresent());
//    }
//
//    @Test
//    void updateBook() {
//        Optional<Book> book = bookRepository.findById(1);
//        book.get().setName("updated_name");
//
//        Book updatedSavedBook = bookRepository.save(book.get());
//        assertNotNull(updatedSavedBook);
//        assertEquals("updated_name", updatedSavedBook.getName());
//
//        log.info("updateBook...");
//        log.info("Book found: " + book.isPresent());
//        log.info("Book name: " + book.get().getName());
//    }
//}
