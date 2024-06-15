package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.models.Basket;
import com.awbd.bookshopspringcloud.models.Book;
import com.awbd.bookshopspringcloud.models.BookBasket;
import com.awbd.bookshopspringcloud.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class BasketRepositoryTest {

    @Mock
    BasketRepository basketRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookBasketRepository bookBasketRepository;

    @Test
    void findByUserId() {
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(user)).thenReturn(user);

        Basket basket = new Basket();
        basket.setUser(user);
        when(basketRepository.save(basket)).thenReturn(basket);

        when(basketRepository.findByUserId(1)).thenReturn(Optional.of(basket));

        Optional<Basket> foundBasket = basketRepository.findByUserId(1);
        assertTrue(foundBasket.isPresent());
        assertNotNull(foundBasket.get());
        assertEquals(basket, foundBasket.get());
    }

    @Test
    void findByUser_notFound() {
        when(basketRepository.findByUserId(anyInt())).thenReturn(Optional.empty());

        Optional<Basket> foundBasket = basketRepository.findByUserId(999);
        assertTrue(foundBasket.isEmpty());
    }

    @Test
    void findBooksFromCurrentBasket() {
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(user)).thenReturn(user);

        Book book1 = new Book("book1", 12);
        when(bookRepository.save(book1)).thenReturn(book1);

        Book book2 = new Book("book2", 20);
        when(bookRepository.save(book2)).thenReturn(book2);

        Basket basket = new Basket();
        basket.setUser(user);
        when(basketRepository.save(basket)).thenReturn(basket);

        BookBasket bookBasket1 = new BookBasket();
        bookBasket1.setBook(book1);
        bookBasket1.setBasket(basket);
        when(bookBasketRepository.save(bookBasket1)).thenReturn(bookBasket1);

        BookBasket bookBasket2 = new BookBasket();
        bookBasket2.setBook(book2);
        bookBasket2.setBasket(basket);
        when(bookBasketRepository.save(bookBasket2)).thenReturn(bookBasket2);

        when(basketRepository.findBooksFromCurrentBasket(1)).thenReturn(List.of(
                new BookFromBasketDetails( book1.getName(), book1.getPrice(), bookBasket1.getCopies(), book1.getId()),
                new BookFromBasketDetails( book2.getName(), book2.getPrice(), bookBasket2.getCopies(), book2.getId())
        ));

        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(1);
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
        when(basketRepository.save(basket)).thenReturn(basket);

        BookBasket bookBasket = new BookBasket();
        bookBasket.setBasket(basket);
        when(bookBasketRepository.save(bookBasket)).thenReturn(bookBasket);

        when(basketRepository.findBooksFromCurrentBasket(1)).thenReturn(List.of());

        List<BookFromBasketDetails> bookFromBasketDetails = basketRepository.findBooksFromCurrentBasket(1);
        assertEquals(0, bookFromBasketDetails.size());
    }
}
