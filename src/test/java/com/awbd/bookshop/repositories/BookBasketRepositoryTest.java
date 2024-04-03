package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.BookBasket;
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
class BookBasketRepositoryTest {
    @Autowired
    BookBasketRepository bookBasketRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BasketRepository basketRepository;
    @Test
    void findBookInBasket() {
        Book book = new Book("book", 12);
        book = bookRepository.save(book);

        Basket basket = new Basket();
        basket = basketRepository.save(basket);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBook(book);
        bookBasket.setBasket(basket);
        bookBasketRepository.save(bookBasket);

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(book.getId(), basket.getId());
        assertTrue(foundBookBasket.isPresent());
        assertNotNull(foundBookBasket.get());
        assertEquals(bookBasket, foundBookBasket.get());
    }

    @Test
    void findBookInBasket_notFoundBook() {
        int nonExistentBookId = 999;

        Basket basket = new Basket();
        basket = basketRepository.save(basket);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBasket(basket);
        bookBasketRepository.save(bookBasket);

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(nonExistentBookId, basket.getId());
        assertTrue(foundBookBasket.isEmpty());
    }
    @Test
    void findBookInBasket_notFoundBasket() {
        int nonExistentBasketId = 999;
        Book book = new Book("book", 12);
        book = bookRepository.save(book);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBook(book);
        bookBasketRepository.save(bookBasket);

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(book.getId(), nonExistentBasketId);
        assertTrue(foundBookBasket.isEmpty());
    }


}