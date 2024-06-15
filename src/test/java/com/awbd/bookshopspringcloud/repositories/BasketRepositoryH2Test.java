package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.models.Basket;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
class BasketRepositoryH2Test {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookBasketRepository bookBasketRepository;

    @Test
    void findByUserId() {
        Optional<Basket> foundBasket = basketRepository.findByUserId(1);
        assertTrue(foundBasket.isPresent());
        assertNotNull(foundBasket.get());

        log.info("findByUserId - current basket ...");
        log.info("Is sent: " + foundBasket.get().getSent());
        log.info("Current cost: " + foundBasket.get().getCost());
        log.info("Basket id: " + foundBasket.get().getId());
        log.info("User email: " + foundBasket.get().getUser().getEmail());
    }

    @Test
    void findByUser_notFound() {
        Optional<Basket> foundBasket = basketRepository.findByUserId(2);
        assertTrue(foundBasket.isEmpty());

        log.info("findByUser_notFound - user with id 2 not have basket...");
        log.info("Has a basket: " + foundBasket.isPresent());
    }

    @Test
    void findBooksFromCurrentBasket() {
        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(2);
        assertFalse(bookFromBasketDetails.isEmpty());
        assertNotNull(bookFromBasketDetails);

        log.info("findBooksFromCurrentBasket...");
        for (BookFromBasketDetails bookFromBasket: bookFromBasketDetails) {
            log.info("Book name: " + bookFromBasket.getName());
            log.info("Book total price: " + bookFromBasket.getPrice());
            log.info("Book number of copies in basket: " + bookFromBasket.getCopies());
            log.info("Book id: " + bookFromBasket.getId());
        }
    }

    @Test
    void findBooksFromCurrentBasket_notFound() {
        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(1);
        assertEquals(0, bookFromBasketDetails.size());

        log.info("findBooksFromCurrentBasket_notFound...");
        log.info("The number of books in basket: " + bookFromBasketDetails.size());
    }
}
