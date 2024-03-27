package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.BasketDetails;
import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.mappers.BasketMapper;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.services.basket.IBasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {
    final IBasketService basketService;
    final BasketMapper mapper;

    public BasketController(IBasketService basketService, BasketMapper mapper) {
        this.basketService = basketService;
        this.mapper = mapper;
    }

    @PostMapping("/createBasket/{userId}")
    public ResponseEntity<BasketDetails> createBasket(
            @PathVariable int userId
    ) {
        Basket basket = basketService.createBasket(userId);
        return ResponseEntity.ok(mapper.bookResponse(basket, null));
    }

    @PatchMapping("/sentOrder/{userId}")
    public ResponseEntity<BasketDetails> sentOrder(
            @PathVariable int userId
    ) {
        Basket basket = basketService.sentOrder(userId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        return ResponseEntity.ok(mapper.bookResponse(basket, books));
    }

    @GetMapping("/getBasket/{userId}")
    public ResponseEntity<BasketDetails> getBasket(
            @PathVariable int userId
    ) {
        Basket basket = basketService.sentOrder(userId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        return ResponseEntity.ok(mapper.bookResponse(basket, books));
    }

    @PostMapping("/addBookToBasket/{bookId}/{basketId}")
    public  ResponseEntity<BasketDetails> addBookToBasket(
            @PathVariable int bookId,
            @PathVariable int basketId
    ) {
        Basket basket = basketService.addBookToBasket(bookId, basketId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
    }

    @DeleteMapping("/removeBookFromBasket/{bookId}/{basketId}")
    public  ResponseEntity<BasketDetails> removeBookFromBasket(
            @PathVariable int bookId,
            @PathVariable int basketId) {
        Basket basket = basketService.removeBookFromBasket(bookId, basketId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
    }

    @PatchMapping("/decrementBookFromBasket/{bookId}/{basketId}")
    public ResponseEntity<BasketDetails> decrementBookFromBasket(
            @PathVariable int bookId,
            @PathVariable int basketId
    ) {
        Basket basket = basketService.decrementBookFromBasket(bookId, basketId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
    }
}
