package com.awbd.bookshop.services.bookbasket;

import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.BookBasket;
import com.awbd.bookshop.repositories.BookBasketRepository;
import com.awbd.bookshop.services.book.IBookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookBasketService implements IBookBasketService {
    private final BookBasketRepository bookBasketRepository;
    private final IBookService bookService;

    public BookBasketService(BookBasketRepository bookBasketRepository,
                             IBookService bookService) {
        this.bookBasketRepository = bookBasketRepository;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Double addBookToBasket(Integer bookId, Basket basket) {
        Book book = bookService.getBookById(bookId);

        BookBasket bookBasket = bookBasketRepository.findBookInBasket(book.getId(), basket.getId()).orElse(null);

        if(bookBasket == null) {
            bookBasket = bookBasketRepository.save(new BookBasket(
                    0,
                    1,
                    book.getPrice(),
                    book,
                    basket
            ));
        } else {
            bookBasket.setCopies(bookBasket.getCopies() + 1);
            bookBasketRepository.save(bookBasket);
        }

        return bookBasket.getPrice();
    }

    @Transactional
    @Override
    public Double removeBookToBasket(int bookId, int basketId) {
        BookBasket bookBasket = bookBasketRepository.findBookInBasket(bookId, basketId).orElseThrow(
                () -> new NoFoundElementException("The book is not in this basket"));

        bookBasketRepository.delete(bookBasket);

        return bookBasket.getPrice() * bookBasket.getCopies();
    }

    @Transactional
    @Override
    public Double decrementBookFromBasket(int bookId, int basketId) {
        BookBasket bookBasket = bookBasketRepository.findBookInBasket(bookId, basketId).orElseThrow(
                () -> new NoFoundElementException("The book is not in this basket"));

        if (bookBasket.getCopies() > 1)
        {
            bookBasket.setCopies(bookBasket.getCopies() - 1);
            bookBasketRepository.save(bookBasket);
        } else {
            bookBasketRepository.delete(bookBasket);
        }

        return bookBasket.getPrice();
    }
}
