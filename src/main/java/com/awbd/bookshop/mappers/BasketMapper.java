package com.awbd.bookshop.mappers;

import com.awbd.bookshop.dtos.BasketDetails;
import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Basket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasketMapper {
    public BasketDetails bookResponse(Basket basket, List<BookFromBasketDetails> books) {
        return new BasketDetails(
                basket.getId(),
                basket.getSent().toString(),
                basket.getUser().getId(),
                basket.getUser().getEmail(),
                basket.getCost(),
                books
        );
    }
}
