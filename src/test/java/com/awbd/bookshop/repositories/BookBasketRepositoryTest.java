package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.BookBasket;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class BookBasketRepositoryTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BasketRepository basketRepository;

    @Mock
    BookBasketRepository bookBasketRepository;

    @Test
    void findBookInBasket() {
        Book book = new Book("book", 12);
        Basket basket = new Basket();
        BookBasket bookBasket = new BookBasket();
        bookBasket.setBook(book);
        bookBasket.setBasket(basket);

        when(bookBasketRepository.findBookInBasket(anyInt(), anyInt())).thenReturn(Optional.of(bookBasket));

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(1, 1);
        assertTrue(foundBookBasket.isPresent());
        assertEquals(bookBasket, foundBookBasket.get());
    }

    @Test
    void findBookInBasket_notFoundBook() {
        when(bookRepository.save(any())).thenReturn(null);

        Basket basket = new Basket();
        when(basketRepository.save(any())).thenReturn(basket);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBasket(basket);
        when(bookBasketRepository.save(any())).thenReturn(bookBasket);

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(anyInt(), anyInt());
        assertTrue(foundBookBasket.isEmpty());
    }

    @Test
    void findBookInBasket_notFoundBasket() {
        Book book = new Book("book", 12);
        when(bookRepository.save(any())).thenReturn(book);

        when(basketRepository.save(any())).thenReturn(null);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBook(book);
        when(bookBasketRepository.save(any())).thenReturn(bookBasket);

        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(anyInt(), anyInt());
        assertTrue(foundBookBasket.isEmpty());
    }
}
