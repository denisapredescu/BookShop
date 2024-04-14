package com.awbd.bookshop.services.basket;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Coupon;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.repositories.BasketRepository;
import com.awbd.bookshop.services.bookbasket.IBookBasketService;
import com.awbd.bookshop.services.coupon.ICouponService;
import com.awbd.bookshop.services.user.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.awbd.bookshop.exceptions.exceptions.DatabaseError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {
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
    private static final Integer USER_ID = 0;
    private static final User USER = new User(
            USER_ID,
            "username",
            "denisa.predescu@gmail.com",
            "Denisa01!",
            "firstName",
            "lastName",
            true
    );
    private static final Integer BASKET_ID = 0;
    private static final Basket BASKET = new Basket(
            BASKET_ID,
            false,
            100,
            USER
    );

    @InjectMocks
    private BasketService basketServiceUnderTest;
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private IBookBasketService bookBasketService;
    @Mock
    private ICouponService couponService;
    @Mock
    private IUserService userService;

    @BeforeEach
    void setUp() {

    }
    @Test
    void createBasket() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);

        var result = basketServiceUnderTest.createBasket(USER_ID);
        verify(userService).getUser(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    void createBasket_DatabaseError_at_getUser() {
        when(userService.getUser(USER_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.createBasket(USER_ID));
        verify(userService).getUser(USER_ID);
        verify(basketRepository, never()).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void createBasket_NoSuchElementException_at_getUser() {
        when(userService.getUser(USER_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.createBasket(USER_ID));
        verify(userService).getUser(USER_ID);
        verify(basketRepository, never()).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void createBasket_already_created() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.of(BASKET));

        var result = basketServiceUnderTest.createBasket(USER_ID);
        verify(userService).getUser(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    void createBasket_DatabaseError_at_save() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.createBasket(USER_ID));
        verify(userService).getUser(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    void sentOrder() {
        BASKET.setCost(10);

        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.of(BASKET));

        BASKET.setSent(true);

        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);

        var result = basketServiceUnderTest.sentOrder(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    public void testSentOrder_CouponApplied() {
        int userId = 123;
        Basket basketWithCost = new Basket();
        basketWithCost.setId(userId);
        basketWithCost.setCost(150);

        Coupon coupon = new Coupon();
        coupon.setDiscount(20.0); // 20% discount

        when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basketWithCost));
        when(couponService.findCoupon(userId)).thenReturn(coupon);

        basketServiceUnderTest.sentOrder(userId);

        // Verify that basket cost is reduced by 20%
        verify(basketRepository).save(argThat(savedBasket -> savedBasket.getCost() == 120));
        verify(couponService).delete(coupon);
    }

    @Test
    public void testSentOrder_CouponNotApplied() {
        int userId = 123;
        Basket basketWithCost = new Basket();
        basketWithCost.setId(userId);
        basketWithCost.setCost(50);

        when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basketWithCost));
        when(couponService.findCoupon(userId)).thenReturn(null);

        basketServiceUnderTest.sentOrder(userId);

        // Verify that basket cost remains the same
        verify(basketRepository).save(argThat(savedBasket -> savedBasket.getCost() == 50));
        verify(couponService, never()).delete(any());
    }

    @Test
    public void testSentOrder_CouponAppliedAndNewCouponIssued() {
        int userId = 123;
        Basket basketWithCost = new Basket();
        basketWithCost.setId(userId);
        basketWithCost.setCost(150);

        Coupon coupon = new Coupon();
        coupon.setDiscount(20.0); // 20% discount

        User user = new User();
        user.setId(userId);

        when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basketWithCost));
        when(couponService.findCoupon(userId)).thenReturn(coupon);
        when(userService.getUser(userId)).thenReturn(user);

        basketServiceUnderTest.sentOrder(userId);

        // Verify that basket cost is reduced by 20%
        verify(basketRepository).save(argThat(savedBasket -> savedBasket.getCost() == 120));
        verify(couponService).delete(coupon);
        verify(couponService).insert(10.0, user);
    }


    @Test
    void sentOrder_DatabaseError_at_findByUserId()  {
        when(basketRepository.findByUserId(USER_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.sentOrder(USER_ID));
        verify(basketRepository, times(1)).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void sentOrder_NoSuchElementException_at_findByUserId()  {
        when(basketRepository.findByUserId(USER_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.sentOrder(USER_ID));
        verify(basketRepository, times(1)).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void sentOrder_NoSuchElementException_cost_equals_zero() {
        BASKET.setCost(0);

        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.of(BASKET));

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.sentOrder(USER_ID));
        verify(basketRepository, times(1)).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void sentOrder_DatabaseError_at_save() {
        BASKET.setCost(10);

        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.of(BASKET));
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.sentOrder(USER_ID));
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    void getBasket_createBasket() {
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);
        when(userService.getUser(USER_ID)).thenReturn(USER);

        var result = basketServiceUnderTest.getBasket(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
        verify(userService).getUser(USER_ID);
        assertEquals(
                BASKET
        , result);
    }

    @Test
    void getBasket() {
        List<BookFromBasketDetails> books = List.of(
                new BookFromBasketDetails("book 1", 50, 1,1),//am pus id=1
                new BookFromBasketDetails("book 2", 25, 2,2)//am pus id=2
        );
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.of(BASKET));

        var result = basketServiceUnderTest.getBasket(USER_ID);
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
        verify(userService, never()).getUser(USER_ID);
        assertEquals(BASKET, result);
    }

    @Test
    void getBasket_DatabaseError_findByUserId() {
        when(basketRepository.findByUserId(USER_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.getBasket(USER_ID));
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
        verify(userService, never()).getUser(USER_ID);
        verify(basketRepository, never()).findBooksFromCurrentBasket(BASKET_ID);
    }

    @Test
    void getBasket_DatabaseError_at_save() {
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);;

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.getBasket(USER_ID));
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository).save(any(Basket.class));
        verify(userService).getUser(USER_ID);
        verify(basketRepository, never()).findBooksFromCurrentBasket(BASKET_ID);
    }

    @Test
    void getBasket_DatabaseError_at_getUser() {
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(userService.getUser(USER_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.getBasket(USER_ID));
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
        verify(userService).getUser(USER_ID);
        verify(basketRepository, never()).findBooksFromCurrentBasket(BASKET_ID);
    }

    @Test
    void getBasket_NoSuchElementException_at_getUser() {
        when(basketRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(userService.getUser(USER_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.getBasket(USER_ID));
        verify(basketRepository).findByUserId(USER_ID);
        verify(basketRepository, never()).save(any(Basket.class));
        verify(userService).getUser(USER_ID);
        verify(basketRepository, never()).findBooksFromCurrentBasket(BASKET_ID);
    }

    @Test
    void addBookToBasket() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.addBookToBasket(BOOK_ID, BASKET)).thenReturn(BOOK.getPrice());

        BASKET.setCost(BASKET.getCost() + BOOK.getPrice());

        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);

        var result = basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    void addBookToBasket_DatabaseError_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void addBookToBasket_NoSuchElementException_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void addBookToBasket_DatabaseError_at_addBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.addBookToBasket(BOOK_ID, BASKET)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void addBookToBasket_NoSuchElementException_at_addBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.addBookToBasket(BOOK_ID, BASKET)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () ->  basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void addBookToBasket_DatabaseError_at_save() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.addBookToBasket(BOOK_ID, BASKET)).thenReturn(BOOK.getPrice());
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).addBookToBasket(BOOK_ID, BASKET);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    void removeBookFromBasket() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.removeBookToBasket(BOOK_ID, BASKET_ID)).thenReturn(BOOK.getPrice());

        BASKET.setCost(BASKET.getCost() - BOOK.getPrice());

        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);

        var result = basketServiceUnderTest.removeBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    void removeBookFromBasket_DatabaseError_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void removeBookFromBasket_NoSuchElementException_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.removeBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void removeBookFromBasket_DatabaseError_at_removeBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.removeBookToBasket(BOOK_ID, BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  basketServiceUnderTest.removeBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void removeBookFromBasket_NoSuchElementException_at_removeBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.removeBookToBasket(BOOK_ID, BASKET_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () ->  basketServiceUnderTest.removeBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void removeBookFromBasket_DatabaseError_at_save() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.removeBookToBasket(BOOK_ID, BASKET_ID)).thenReturn(BOOK.getPrice());
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.removeBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    void decrementBookFromBasket() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.decrementBookFromBasket(BOOK_ID, BASKET_ID)).thenReturn(BOOK.getPrice());

        BASKET.setCost(BASKET.getCost() - BOOK.getPrice());

        when(basketRepository.save(any(Basket.class))).thenReturn(BASKET);

        var result = basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).save(any(Basket.class));
        assertEquals(BASKET, result);
    }

    @Test
    void decrementBookFromBasket_DatabaseError_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void decrementBookFromBasket_NoSuchElementException_at_findById() {
        when(basketRepository.findById(BASKET_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService, never()).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void decrementBookFromBasket_DatabaseError_at_decrementBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.decrementBookFromBasket(BOOK_ID, BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void decrementBookFromBasket_NoSuchElementException_at_decrementBook() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.decrementBookFromBasket(BOOK_ID, BASKET_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository, never()).save(any(Basket.class));
    }

    @Test
    void decrementBookFromBasket_DatabaseError_at_save() {
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(bookBasketService.decrementBookFromBasket(BOOK_ID, BASKET_ID)).thenReturn(BOOK.getPrice());
        when(basketRepository.save(any(Basket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> basketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(basketRepository).findById(BASKET_ID);
        verify(bookBasketService).decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    public void testFindBooksFromCurrentBasket_WithBooks() {
        int basketId = 123;
        List<BookFromBasketDetails> expectedBooks = new ArrayList<>();
        expectedBooks.add(new BookFromBasketDetails("Book1", 20.0, 2,1));
        expectedBooks.add(new BookFromBasketDetails("Book2", 30.0, 1,2));

        when(basketRepository.findBooksFromCurrentBasket(basketId)).thenReturn(expectedBooks);

        List<BookFromBasketDetails> actualBooks = basketServiceUnderTest.findBooksFromCurrentBasket(basketId);

        assertEquals(expectedBooks.size(), actualBooks.size());
        assertEquals(expectedBooks.get(0).getName(), actualBooks.get(0).getName());
        assertEquals(expectedBooks.get(0).getPrice(), actualBooks.get(0).getPrice());
        assertEquals(expectedBooks.get(0).getCopies(), actualBooks.get(0).getCopies());
        assertEquals(expectedBooks.get(1).getName(), actualBooks.get(1).getName());
        assertEquals(expectedBooks.get(1).getPrice(), actualBooks.get(1).getPrice());
        assertEquals(expectedBooks.get(1).getCopies(), actualBooks.get(1).getCopies());
    }

    @Test
    public void findBooksFromCurrentBasket_WithoutBooks() {
        int basketId = 123;
        List<BookFromBasketDetails> expectedBooks = new ArrayList<>();
        when(basketRepository.findBooksFromCurrentBasket(basketId)).thenReturn(expectedBooks);

        List<BookFromBasketDetails> actualBooks = basketServiceUnderTest.findBooksFromCurrentBasket(basketId);
        assertTrue(actualBooks.isEmpty());
    }

    @Test
    public void findBooksFromCurrentBasket_NullResult() {
        int basketId = 123;
        when(basketRepository.findBooksFromCurrentBasket(basketId)).thenReturn(null);

        List<BookFromBasketDetails> actualBooks = basketServiceUnderTest.findBooksFromCurrentBasket(basketId);
        assertNull(actualBooks);
    }

}