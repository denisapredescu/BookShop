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

//    @PostMapping("/createBasket/{userId}")
//    public ResponseEntity<BasketDetails> createBasket(
//            @PathVariable int userId
//    ) {
//        Basket basket = basketService.createBasket(userId);
//        return ResponseEntity.ok(mapper.bookResponse(basket, null));
//    }

//    @RequestMapping("/createBasket/{userId}")
//    public ModelAndView createBasketView(
//            @PathVariable int userId,
//            Model model
//    ) {
//        Basket basket = basketService.createBasket(userId);
//        model.addAttribute(basket);
//        return new ModelAndView("redirect:/");
//    }

//    @PatchMapping("/sentOrder/{userId}")
//    public ResponseEntity<BasketDetails> sentOrder(
//            @PathVariable int userId
//    ) {
//        Basket basket = basketService.sentOrder(userId);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
//        return ResponseEntity.ok(mapper.bookResponse(basket, books));
//    }
    @RequestMapping("/sentOrder")
    public ModelAndView sentOrder(Model model)
    {
        int userId = getCurrentUserId();
        Basket basket = basketService.sentOrder(userId);
        model.addAttribute(basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        model.addAttribute(books);
        return new ModelAndView("redirect:/basket/myBasket");
    }
//    @GetMapping("/getBasket/{userId}")
//    public ResponseEntity<BasketDetails> getBasket(
//            @PathVariable int userId
//    ) {
//        Basket basket = basketService.getBasket(userId);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
//        return ResponseEntity.ok(mapper.bookResponse(basket, books));
//    }
    @GetMapping("/myBasket")
    public ModelAndView getBasket(Model model)
    {
        int userId = getCurrentUserId();
        Basket basket = basketService.getBasket(userId);
        model.addAttribute("basket",basket);
        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
        model.addAttribute("books",books);
        return new ModelAndView("basketView");
    }
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return userService.getId(authentication.getName());
        }
        return 0;
    }

//    @PostMapping("/addBookToBasket/{bookId}/{basketId}")
//    public  ResponseEntity<BasketDetails> addBookToBasket(
//            @PathVariable int bookId,
//            @PathVariable int basketId
//    ) {
//        Basket basket = basketService.addBookToBasket(bookId, basketId);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
//        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
//    }

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

//    @DeleteMapping("/removeBookFromBasket/{bookId}/{basketId}")
//    public  ResponseEntity<BasketDetails> removeBookFromBasket(
//            @PathVariable int bookId,
//            @PathVariable int basketId) {
//        Basket basket = basketService.removeBookFromBasket(bookId, basketId);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
//        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
//    }
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

    // @RequestMapping("/delete/{id}")
    //    public ModelAndView deleteBook(
    //            @PathVariable int id
    //    ){
    //        bookService.deleteBook(id);
    //        return new ModelAndView("redirect:/book");
    //    }

//    @PatchMapping("/decrementBookFromBasket/{bookId}/{basketId}")
//    public ResponseEntity<BasketDetails> decrementBookFromBasket(
//            @PathVariable int bookId,
//            @PathVariable int basketId
//    ) {
//        Basket basket = basketService.decrementBookFromBasket(bookId, basketId);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
//        return  ResponseEntity.ok(mapper.bookResponse(basket, books));
//    }
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
