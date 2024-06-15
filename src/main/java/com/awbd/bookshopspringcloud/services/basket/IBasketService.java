package com.awbd.bookshopspringcloud.services.basket;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.models.Basket;

import java.util.List;

public interface IBasketService {
    Basket createBasket(int userId);

    List<BookFromBasketDetails> findBooksFromCurrentBasket(int basketId);

    Basket sentOrder(int userId);
    Basket sentOrderFallback(int userId);
    Basket getBasket(int userId);

    Basket addBookToBasket(int bookId, int basketId);

    Basket removeBookFromBasket(int bookId, int basketId);

    Basket decrementBookFromBasket(int bookId, int basketId);
}
