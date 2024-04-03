package com.awbd.bookshop.services.bookbasket;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Basket;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IBookBasketService {
    Double addBookToBasket(Integer bookId, Basket basket);
    Double removeBookToBasket(int bookId, int basketId);
    Double decrementBookFromBasket(int bookId, int basketId);
}
