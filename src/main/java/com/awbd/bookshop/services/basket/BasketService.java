package com.awbd.bookshop.services.basket;

import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.repositories.BasketRepository;
import com.awbd.bookshop.services.bookbasket.IBookBasketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class BasketService implements IBasketService {
    private final BasketRepository basketRepository;
    private final IBookBasketService bookBasketService;

    public BasketService(BasketRepository basketRepository,
                         IBookBasketService bookBasketService) {
        this.basketRepository = basketRepository;
        this.bookBasketService = bookBasketService;
    }

//    private final UserService userService;

//    public BasketService(BasketRepository basketRepository, BookBasketService bookBasketService, UserService userService) {
//        this.basketRepository = basketRepository;
//        this.bookBasketService = bookBasketService;
//        this.userService = userService;
//    }

//    @Transactional
//    @Override
//    public Basket createBasket(String token, int userId) {
//        JwtUtil.verifyIsLoggedIn(token);
//
//        User user = userService.getUser(userId);
//
//        Basket basket = basketRepository.findByUserId(userId).orElse(null);
//
//        if (basket == null) {
//            return basketRepository.save(new Basket(
//                    0,
//                    false,
//                    0,
//                    user
//            ));
//        }
//
//        return basket;
//    }

//    @Transactional
//    @Override
//    public Basket sentOrder(String token, int userId) {
//        JwtUtil.verifyIsLoggedIn(token);
//
//        Basket basket = basketRepository.findByUserId(userId).orElseThrow(
//                () -> new NoSuchElementException("User does not have a current basket"));
//
//        if (basket.getCost() == 0)
//            throw new NoSuchElementException("User does not have books in basket");
//
//        basket.setSent(true);
//        return basketRepository.save(basket);
//    }

//    @Transactional
//    @Override
//    public BasketDetails getBasket(String token, int userId) {
//        JwtUtil.verifyIsLoggedIn(token);
//
//        Basket basket =  basketRepository.findByUserId(userId).orElse(null);
//
//        if (basket == null)
//            basket = basketRepository.save(new Basket(
//                    0,
//                    false,
//                    0,
//                    userService.getUser(userId)
//            ));
//
//        return new BasketDetails(
//                basket.getId(),
//                basket.getSent().toString(),
//                basket.getUser().getId(),
//                basket.getUser().getEmail(),
//                basket.getCost(),
//                basketRepository.findBooksFromCurrentBasket(basket.getId())
//        );
//    }

    @Override
    public Basket createBasket(String token, int userId) {
        return null;
    }

    @Override
    public Basket sentOrder(String token, int userId) {
        return null;
    }

    @Transactional
    @Override
    public Basket addBookToBasket(String token, int bookId, int basketId) {
//        JwtUtil.verifyIsLoggedIn(token);

        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id")
        );

        Integer bookPriceInBasket = bookBasketService.addBookToBasket(bookId, basket);

        basket.setCost(basket.getCost() + bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket removeBookFromBasket(String token, int bookId, int basketId) {
//        JwtUtil.verifyIsLoggedIn(token);

        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id"));

        Integer bookPriceInBasket = bookBasketService.removeBookToBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket decrementBookFromBasket(String token, int bookId, int basketId) {
//        JwtUtil.verifyIsLoggedIn(token);

        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id"));

        Integer bookPriceInBasket = bookBasketService.decrementBookFromBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }
}
