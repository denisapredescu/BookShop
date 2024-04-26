package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.BasketDetails;
import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.mappers.BasketMapper;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.services.basket.IBasketService;
import com.awbd.bookshop.services.user.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ModelAndView sentOrder(Model model)
    {
        int userId = userService.getCurrentUserId();
        Basket basket = basketService.sentOrder(userId);
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
