package com.awbd.bookshopspringcloud.services.basket;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshopspringcloud.models.Basket;
import com.awbd.bookshopspringcloud.models.Coupon;
import com.awbd.bookshopspringcloud.models.SalesOff;
import com.awbd.bookshopspringcloud.models.User;
import com.awbd.bookshopspringcloud.repositories.BasketRepository;
import com.awbd.bookshopspringcloud.services.SalesOffServiceProxy;
import com.awbd.bookshopspringcloud.services.bookbasket.IBookBasketService;
import com.awbd.bookshopspringcloud.services.coupon.ICouponService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService implements IBasketService {
    private final BasketRepository basketRepository;
    private final IBookBasketService bookBasketService;
    private final IUserService userService;
    private  final ICouponService couponService;
    private static final Logger logger = LoggerFactory.getLogger(BasketService.class);
    SalesOffServiceProxy salesOffServiceProxy;

    public BasketService(
            BasketRepository basketRepository,
            IBookBasketService bookBasketService,
            IUserService userService,
            ICouponService couponService,
            SalesOffServiceProxy salesOffServiceProxy) {
        this.basketRepository = basketRepository;
        this.bookBasketService = bookBasketService;
        this.userService = userService;
        this.couponService = couponService;
        this.salesOffServiceProxy = salesOffServiceProxy;
    }

    @Transactional
    @Override
    public Basket createBasket(int userId) {
        User user = userService.getUser(userId);

        Basket basket = basketRepository.findByUserId(userId).orElse(null);

        if (basket == null) {
            return basketRepository.save(new Basket(
                    0,
                    false,
                    0,
                    user
            ));
        }

        return basket;
    }

    @Transactional
    @Override
    public Basket sentOrder(int userId) {
        int booksCount = 0;
        int bookDiscount = 0;

        Basket basket = basketRepository.findByUserId(userId).orElseThrow(
                () -> new NoFoundElementException("User does not have a current basket"));

        if (basket.getCost() == 0)
            throw new NoFoundElementException("User does not have books in basket");


        //Sales Off logic
        SalesOff salesOff = salesOffServiceProxy.getingSalesOff();

        List<String> salesOffCategories = Arrays.asList(salesOff.getCategories()).stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .distinct()
                .toList();

        logger.info(salesOff.getVersionId());
        List<BookFromBasketDetails> booksBasket = basketRepository.findBooksFromCurrentBasket(basket.getId());
        for (BookFromBasketDetails book : booksBasket) {
            int okCategory = 0;
            List<String> bookCategories = Arrays.asList(book.getCategories()).stream()
                    .flatMap(s -> Arrays.stream(s.split(",")))
                    .distinct()
                    .toList();
            for(String bookCategory : bookCategories) {
                if(salesOffCategories.contains(bookCategory)){
                    okCategory = okCategory + 1;

                }
            }
            if(okCategory!=0 || salesOff.getAuthors().contains(book.getAuthor_name())){
                booksCount = booksCount + book.getCopies();
            }
        }

        if(booksCount>=salesOff.getLowNoBooks() && booksCount<=salesOff.getMediumNoBooks())
            bookDiscount = salesOff.getLowSalesOff();
        if (booksCount>salesOff.getMediumNoBooks() && booksCount<=salesOff.getHighNoBooks()) {
            bookDiscount = salesOff.getMediumSalesOff();
        }
        if (booksCount>salesOff.getHighNoBooks())
            bookDiscount = salesOff.getHighSalesOff();
        logger.info(String.valueOf(bookDiscount));
        // coupon logic
        Coupon coupon = couponService.findCoupon(userId);

        if (coupon != null) {
            basket.setCost((1 - (coupon.getDiscount()+bookDiscount)/100) * basket.getCost());
            coupon.setUser(null);
            couponService.delete(coupon);
        }

        if (basket.getCost() > 100) {
            User user = userService.getUser(userId);
            couponService.insert(10.0, user);

        }
        basket.setCost((1 - (double) bookDiscount /100) * basket.getCost());
        basket.setSent(true);
        return basketRepository.save(basket);
    }


    @Transactional
    @Override
    public Basket sentOrderFallback(int userId) {

        Basket basket = basketRepository.findByUserId(userId).orElseThrow(
                () -> new NoFoundElementException("User does not have a current basket"));

        if (basket.getCost() == 0)
            throw new NoFoundElementException("User does not have books in basket");

        // coupon logic
        Coupon coupon = couponService.findCoupon(userId);

        if (coupon != null) {
            basket.setCost((1 - coupon.getDiscount()/100) * basket.getCost());
            coupon.setUser(null);
            couponService.delete(coupon);
        }

        if (basket.getCost() > 100) {
            User user = userService.getUser(userId);
            couponService.insert(10.0, user);

        }
        basket.setSent(true);
        return basketRepository.save(basket);
    }
    @Transactional
    @Override
    public Basket getBasket(int userId) {
        Basket basket = basketRepository.findByUserId(userId).orElse(null);

        if (basket == null)
            basket = basketRepository.save(new Basket(
                    0,
                    false,
                    0,
                    userService.getUser(userId)
            ));

        return basket;
    }

    @Transactional
    @Override
    public List<BookFromBasketDetails> findBooksFromCurrentBasket(int basketId) {
        return basketRepository.findBooksFromCurrentBasket(basketId);
    }

    @Transactional
    @Override
    public Basket addBookToBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoFoundElementException("Does not exist a basket with this id")
        );

        Double bookPriceInBasket = bookBasketService.addBookToBasket(bookId, basket);

        basket.setCost(basket.getCost() + bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket removeBookFromBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoFoundElementException("Does not exist a basket with this id"));

        Double bookPriceInBasket = bookBasketService.removeBookToBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket decrementBookFromBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoFoundElementException("Does not exist a basket with this id"));

        Double bookPriceInBasket = bookBasketService.decrementBookFromBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }
}
