package com.awbd.bookshop.services.basket;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Basket;

import java.util.List;

public interface IBasketService {
    Basket createBasket(int userId);
    List<BookFromBasketDetails> findBooksFromCurrentBasket(int basketId);
    Basket sentOrder(int userId);
    Basket getBasket(int userId);
    Basket addBookToBasket(int bookId, int basketId);
    Basket removeBookFromBasket(int bookId, int basketId);
    Basket decrementBookFromBasket(int bookId, int basketId);

}
