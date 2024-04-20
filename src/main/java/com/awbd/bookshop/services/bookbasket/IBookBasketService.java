package com.awbd.bookshop.services.bookbasket;

import com.awbd.bookshop.models.Basket;

public interface IBookBasketService {
    Double addBookToBasket(Integer bookId, Basket basket);

    Double removeBookToBasket(int bookId, int basketId);

    Double decrementBookFromBasket(int bookId, int basketId);
}
