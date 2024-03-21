package com.awbd.bookshop.services.basket;

import com.awbd.bookshop.models.Basket;

public interface IBasketService {
    Basket createBasket(String token, int userId);
    Basket sentOrder(String token, int userId);
//    BasketDetails getBasket(String token, int userId);
    Basket addBookToBasket(String token, int bookId, int basketId);
    Basket removeBookFromBasket(String token, int bookId, int basketId);
    Basket decrementBookFromBasket(String token, int bookId, int basketId);

}
