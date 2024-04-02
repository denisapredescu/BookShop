package com.awbd.bookshop.repositories;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.*;
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
class BasketRepositoryTest {
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
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);

        Basket basket = new Basket();
        basket.setUser(user);
        Basket savedBasket = basketRepository.save(basket);
        assertNotNull(savedBasket);

        Optional<Basket> foundBasket = basketRepository.findByUserId(savedUser.getId());
        assertTrue(foundBasket.isPresent());
        assertNotNull(foundBasket.get());
        assertEquals(savedBasket, foundBasket.get());
    }

    @Test
    void findByUser_notFound() {
        int nonExistentUserId = 999;

        Optional<Basket> foundBasket = basketRepository.findByUserId(nonExistentUserId);
        assertTrue(foundBasket.isEmpty());
    }

    @Test
    void findBooksFromCurrentBasket() {
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);

        Book book1 = new Book("book1", 12);
        book1 = bookRepository.save(book1); // Save the book

        Book book2 = new Book("book2", 20);
        book2 = bookRepository.save(book2); // Save the book

        Basket basket = new Basket();
        basket.setUser(user);
        Basket savedBasket = basketRepository.save(basket);
        assertNotNull(savedBasket);

        BookBasket bookBasket1 = new BookBasket();
        bookBasket1.setBook(book1);
        bookBasket1.setBasket(savedBasket);
        BookBasket savedBookBasket1 = bookBasketRepository.save(bookBasket1);
        assertNotNull(savedBookBasket1);

        BookBasket bookBasket2 = new BookBasket();
        bookBasket2.setBook(book2);
        bookBasket2.setBasket(savedBasket);
        BookBasket savedBookBasket2 = bookBasketRepository.save(bookBasket2);
        assertNotNull(savedBookBasket2);

        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(savedBasket.getId());
        assertEquals(2, bookFromBasketDetails.size());
        assertEquals(book1.getName(), bookFromBasketDetails.get(0).getName());
        assertEquals(bookBasket1.getCopies(), bookFromBasketDetails.get(0).getCopies());
        assertEquals(book1.getId(), bookFromBasketDetails.get(0).getId());

        assertEquals(book2.getName(), bookFromBasketDetails.get(1).getName());
        assertEquals(bookBasket2.getCopies(), bookFromBasketDetails.get(1).getCopies());
        assertEquals(book2.getId(), bookFromBasketDetails.get(1).getId());
    }

    @Test
    void findBooksFromCurrentBasket_notFound() {
        Basket basket = new Basket();
        Basket savedBasket = basketRepository.save(basket);
        assertNotNull(savedBasket);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBasket(savedBasket);
        BookBasket savedBookBasket = bookBasketRepository.save(bookBasket);
        assertNotNull(savedBookBasket);

        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(savedBasket.getId());
        assertEquals(0, bookFromBasketDetails.size());
    }
}