package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.mappers.BasketMapper;
import com.awbd.bookshopspringcloud.models.Basket;
import com.awbd.bookshopspringcloud.services.basket.IBasketService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {
    final IBasketService basketService;
    final BasketMapper mapper;
    final IUserService userService;

    public BasketController(IBasketService basketService, BasketMapper mapper, IUserService userService) {
        this.basketService = basketService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @RequestMapping("/sentOrder")
    @CircuitBreaker(name = "salesOffId", fallbackMethod = "sentOrderFallback")
    public ModelAndView sentOrder(Model model)
    {
        int userId = userService.getCurrentUserId();
        Basket basket = basketService.sentOrder(userId);
        model.addAttribute(basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        model.addAttribute(books);
        return new ModelAndView("redirect:/basket/myBasket");
    }


    public ModelAndView sentOrderFallback(Model model, Throwable throwable)
    {
        int userId = userService.getCurrentUserId();
        Basket basket = basketService.sentOrderFallback(userId);
        model.addAttribute(basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        model.addAttribute(books);
        return new ModelAndView("redirect:/basket/myBasket");
    }
    @GetMapping("/myBasket")
    public ModelAndView getBasket(Model model)
    {
        int userId = userService.getCurrentUserId();
        Basket basket = basketService.getBasket(userId);
        model.addAttribute("basket",basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        model.addAttribute("books",books);
        return new ModelAndView("basketView");
    }

    @RequestMapping("/addBookToBasket/{bookId}/{basketId}")
    public  ModelAndView addBookToBasket(
            @PathVariable int bookId,
            @PathVariable int basketId,
            Model model
    ) {
        Basket basket = basketService.addBookToBasket(bookId, basketId);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        model.addAttribute(basket);
        model.addAttribute(books);
        return new ModelAndView("redirect:/basket/myBasket");
    }

    @RequestMapping("/removeBookFromBasket/{bookId}/{basketId}")
    public  ModelAndView removeBookFromBasket(
            @PathVariable int bookId,
            @PathVariable int basketId,
            Model model) {
        Basket basket = basketService.removeBookFromBasket(bookId, basketId);
        model.addAttribute("basket",basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        model.addAttribute("books",books);
        return new ModelAndView("redirect:/basket/myBasket");
    }

    @RequestMapping("/decrementBookFromBasket/{bookId}/{basketId}")
    public ModelAndView decrementBookFromBasket(
            @PathVariable int bookId,
            @PathVariable int basketId,
            Model model
    ) {
        Basket basket = basketService.decrementBookFromBasket(bookId, basketId);
        model.addAttribute(basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
        model.addAttribute(books);
        return new ModelAndView("redirect:/basket/myBasket");
    }
}
