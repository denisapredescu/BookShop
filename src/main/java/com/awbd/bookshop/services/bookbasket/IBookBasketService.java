package com.awbd.bookshop.services.bookbasket;

import com.awbd.bookshop.models.Basket;

public interface IBookBasketService {
    Integer addBookToBasket(Integer bookId, Basket basket);
    Integer removeBookToBasket(int bookId, int basketId);
    Integer decrementBookFromBasket(int bookId, int basketId);
}
