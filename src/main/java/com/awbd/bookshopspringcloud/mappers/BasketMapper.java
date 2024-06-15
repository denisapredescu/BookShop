package com.awbd.bookshopspringcloud.mappers;

import com.awbd.bookshopspringcloud.dtos.BasketDetails;
import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.models.Basket;
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
