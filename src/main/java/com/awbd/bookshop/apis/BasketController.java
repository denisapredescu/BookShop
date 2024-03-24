package com.awbd.bookshop.apis;

import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.services.basket.IBasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {
    IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/createBasket/{userId}")
    public ResponseEntity<Basket> createBasket(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int userId) {
        return ResponseEntity.ok(basketService.createBasket(token, userId));
    }

    @PatchMapping("/sentOrder/{userId}")
    public ResponseEntity<Basket> sentOrder(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int userId) {
        return ResponseEntity.ok(basketService.sentOrder(token, userId));
    }

//    @GetMapping("/getBasket/{userId}")
//    public ResponseEntity<BasketDetails> getBasket(
//            @RequestHeader(name = "userToken") String token,
//            @PathVariable int userId) {
//        return ResponseEntity.ok(basketService.getBasket(token, userId));
//    }

    @PostMapping("/addBookToBasket/{bookId}/{basketId}")
    public  ResponseEntity<Basket> addBookToBasket(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int bookId,
            @PathVariable int basketId) {
        return  ResponseEntity.ok(basketService.addBookToBasket(token, bookId, basketId));
    }

    @DeleteMapping("/removeBookFromBasket/{bookId}/{basketId}")
    public  ResponseEntity<Basket> removeBookFromBasket(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int bookId,
            @PathVariable int basketId) {
        return  ResponseEntity.ok(basketService.removeBookFromBasket(token, bookId, basketId));
    }

    @PatchMapping("/decrementBookFromBasket/{bookId}/{basketId}")
    public ResponseEntity<Basket> decrementBookFromBasket(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int bookId,
            @PathVariable int basketId) {
        return ResponseEntity.ok(basketService.decrementBookFromBasket(token, bookId, basketId));
    }
}
