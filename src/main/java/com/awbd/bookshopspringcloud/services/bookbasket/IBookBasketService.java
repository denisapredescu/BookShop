package com.awbd.bookshopspringcloud.services.bookbasket;

import com.awbd.bookshopspringcloud.models.Basket;

public interface IBookBasketService {
    Double addBookToBasket(Integer bookId, Basket basket);

    Double removeBookToBasket(int bookId, int basketId);

    Double decrementBookFromBasket(int bookId, int basketId);
}
