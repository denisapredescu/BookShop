package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.BookBasket;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
class BookBasketRepositoryH2Test {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BookBasketRepository bookBasketRepository;

    @Test
    void findBookInBasket() {
        Optional<BookBasket> foundBookBasket = bookBasketRepository.findBookInBasket(1, 2);
        assertTrue(foundBookBasket.isPresent());

        log.info("findBookInBasket...");
        log.info("Book basket id: " + String.valueOf(foundBookBasket.get().getId()));
        log.info("Number of copies of the book in basket: " + foundBookBasket.get().getCopies());
        log.info("Price of the copies of books: " + foundBookBasket.get().getPrice());
        log.info("Book name: " + foundBookBasket.get().getBook().getName());
        log.info("Basket id: " + foundBookBasket.get().getBasket().getId());
    }

    @Test
    void findBookInBasket_notFoundBook() {
        Optional<BookBasket> bookBasket = bookBasketRepository.findBookInBasket(1, 3);
        assertTrue(bookBasket.isEmpty());

        log.info("findBookInBasket_notFoundBook...");
        log.info("This book is not in basket: " + bookBasket.isEmpty());
    }
}
