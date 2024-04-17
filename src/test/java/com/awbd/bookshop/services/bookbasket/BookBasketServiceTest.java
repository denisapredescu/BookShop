package com.awbd.bookshop.services.bookbasket;

import com.awbd.bookshop.exceptions.exceptions.DatabaseError;
import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.BookBasket;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.repositories.BookBasketRepository;
import com.awbd.bookshop.services.book.BookService;
import com.awbd.bookshop.services.book.IBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookBasketServiceTest {
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
    private static final Integer BASKET_ID = 0;
    private static final Basket BASKET = new Basket(
            BASKET_ID,
            false,
            0,
            new User(
                0,
                "denisapredescu",
                "Denisa01!",
                "denisa.predescu@gmail.com",
                "firstName",
                "lastName",
                true
            )
    );
    private static final BookBasket BOOK_BASKET = new BookBasket(
            0,
            1,
            BOOK.getPrice(),
            BOOK,
            BASKET
    );

    @InjectMocks
    private BookBasketService bookBasketServiceUnderTest;
    @Mock
    private BookBasketRepository bookBasketRepository;
    @Mock
    private IBookService bookService;

    @Test
    void addBookToBasket() {
        when(bookService.getBookById(BOOK_ID)).thenReturn(BOOK);
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.empty());
        when(bookBasketRepository.save(any(BookBasket.class))).thenReturn(BOOK_BASKET);

        var result = bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET);
        verify(bookService).getBookById(BOOK_ID);
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).save(any(BookBasket.class));
        assertEquals(BOOK_BASKET.getPrice(), result);
    }

    @Test
    void addBookToBasket_NoSuchElementException_at_getBookById() {
        when(bookService.getBookById(any())).thenThrow(NoFoundElementException.class);

        assertThrows(NoFoundElementException.class, () -> bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, any(Basket.class)));
        verify(bookService, times(1)).getBookById(any());
        verify(bookBasketRepository, never()).findBookInBasket(any(), any());
        verify(bookBasketRepository, never()).save(any(BookBasket.class));
    }

    @Test
    void addBookToBasket_DatabaseError_at_getBookById() {
        when(bookService.getBookById(any())).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, any(Basket.class)));
        verify(bookService, times(1)).getBookById(any());
        verify(bookBasketRepository, never()).findBookInBasket(any(), any());
        verify(bookBasketRepository, never()).save(any(BookBasket.class));
    }
    @Test
    void addBookToBasket_increase_number_of_same_book() {
        when(bookService.getBookById(BOOK_ID)).thenReturn(BOOK);
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));

        BOOK_BASKET.setCopies(BOOK_BASKET.getCopies() + 1);

        when(bookBasketRepository.save(any(BookBasket.class))).thenReturn(BOOK_BASKET);

        var result = bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET);
        verify(bookService).getBookById(BOOK_ID);
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).save(any(BookBasket.class));
        assertEquals(BOOK_BASKET.getPrice(), result);
    }

    @Test
    void addBookToBasket_DatabaseError_at_save() {
        when(bookService.getBookById(BOOK_ID)).thenReturn(BOOK);
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.empty());
        when(bookBasketRepository.save(any(BookBasket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET));
        verify(bookService, times(1)).getBookById(BOOK_ID);
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, times(1)).save(any(BookBasket.class));
    }

    @Test
    void addBookToBasket_increase_number_DatabaseError_at_save() {
        when(bookService.getBookById(BOOK_ID)).thenReturn(BOOK);
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));
        when(bookBasketRepository.save(any(BookBasket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookBasketServiceUnderTest.addBookToBasket(BOOK_ID, BASKET));
        verify(bookService, times(1)).getBookById(BOOK_ID);
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, times(1)).save(any(BookBasket.class));
    }


    @Test
    void removeBookToBasket() {
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));

        var result = bookBasketServiceUnderTest.removeBookToBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).delete(BOOK_BASKET);
        assertEquals(BOOK_BASKET.getPrice() * BOOK_BASKET.getCopies(), result);
    }

    @Test
    void removeBookToBasket_NoSuchElementException() {
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenThrow(NoFoundElementException.class);

        assertThrows(NoFoundElementException.class, () -> bookBasketServiceUnderTest.removeBookToBasket(BOOK_ID, BASKET_ID));
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, never()).delete(any());
    }

    @Test
    void removeBookToBasket_DatabaseError_at_findBookInBasket() {
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () -> bookBasketServiceUnderTest.removeBookToBasket(BOOK_ID, BASKET_ID));
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, never()).delete(any());
    }

    @Test
    void decrementBookFromBasket() {
        BOOK_BASKET.setCopies(10);

        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));

        BOOK_BASKET.setCopies(BOOK_BASKET.getCopies() - 1);

        when(bookBasketRepository.save(any(BookBasket.class))).thenReturn(BOOK_BASKET);

        var result = bookBasketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).save(any(BookBasket.class));
        assertEquals(BOOK_BASKET.getPrice(), result);
    }

    @Test
    void decrementBookFromBasket_NoSuchElementException() {
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenThrow(NoFoundElementException.class);

        assertThrows(NoFoundElementException.class, () ->  bookBasketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, never()).save(any(BookBasket.class));
    }

    @Test
    void decrementBookFromBasket_DatabaseError_at_findBookInBasket() {
        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  bookBasketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, never()).save(any(BookBasket.class));
    }

    @Test
    void decrementBookFromBasket_DatabaseError_at_save() {
        BOOK_BASKET.setCopies(10);

        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));

        BOOK_BASKET.setCopies(BOOK_BASKET.getCopies() - 1);

        when(bookBasketRepository.save(any(BookBasket.class))).thenThrow(DatabaseError.class);

        assertThrows(DatabaseError.class, () ->  bookBasketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID));
        verify(bookBasketRepository, times(1)).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository, times(1)).save(any(BookBasket.class));
    }

    @Test
    void decrementBookFromBasket_remove_book() {
        BOOK_BASKET.setCopies(1);

        when(bookBasketRepository.findBookInBasket(BOOK_ID, BASKET_ID)).thenReturn(Optional.of(BOOK_BASKET));

        var result = bookBasketServiceUnderTest.decrementBookFromBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).findBookInBasket(BOOK_ID, BASKET_ID);
        verify(bookBasketRepository).delete(BOOK_BASKET);
        assertEquals(BOOK_BASKET.getPrice(), result);
    }
}