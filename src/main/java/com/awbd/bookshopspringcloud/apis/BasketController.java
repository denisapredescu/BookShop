package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.mappers.BasketMapper;
import com.awbd.bookshopspringcloud.models.Basket;
import com.awbd.bookshopspringcloud.services.basket.IBasketService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.Parameter;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/basket")
@Tag(name = "Basket API", description = "Endpoints for managing baskets")
public class BasketController {
    final IBasketService basketService;
    final BasketMapper mapper;
    final IUserService userService;

    public BasketController(IBasketService basketService, BasketMapper mapper, IUserService userService) {
        this.basketService = basketService;
        this.mapper = mapper;
        this.userService = userService;
    }
    //create basket
    @Operation(summary = "Create a empty basket for a user", responses = {
            @ApiResponse(responseCode = "200", description = "Basket created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/createBasket/{userId}")
    public ResponseEntity<Basket> createBasket(@PathVariable @Parameter(description = "User ID", required = true) int userId) {
        Basket basket = basketService.createBasket(userId);

        Link sendLink = linkTo(methodOn(BasketController.class).sentOrder(userId)).withRel("sendOrder");
        basket.add(sendLink);

        Link createLink = linkTo(methodOn(BasketController.class).createBasket(userId)).withSelfRel();
        basket.add(createLink);

        return ResponseEntity.ok(basket);
    }
//    @RequestMapping("/sentOrder")
//    @CircuitBreaker(name = "salesOffId", fallbackMethod = "sentOrderFallback")
//    public ModelAndView sentOrder(Model model)
//    {
//        int userId = userService.getCurrentUserId();
//        Basket basket = basketService.sentOrder(userId);
//        model.addAttribute(basket);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
//        model.addAttribute(books);
//        return new ModelAndView("redirect:/basket/myBasket");
//    }
    // public ModelAndView sentOrderFallback(Model model, Throwable throwable)
//    {
//        int userId = userService.getCurrentUserId();
//        Basket basket = basketService.sentOrderFallback(userId);
//        model.addAttribute(basket);
//        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basket.getId());
//        model.addAttribute(books);
//        return new ModelAndView("redirect:/basket/myBasket");
//    }
    @Operation(summary = "Send an order from the bookstore. All the books from basket will be ordered", responses = {
            @ApiResponse(responseCode = "200", description = "Order sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/sentOrder/{userId}")
    @CircuitBreaker(name = "salesOffId", fallbackMethod = "sentOrderFallback")
    public ResponseEntity<Basket> sentOrder(int userId)
    {
        Basket basket = basketService.sentOrder(userId);
        Link sendLink = linkTo(methodOn(BasketController.class).sentOrder(userId)).withSelfRel();
        basket.add(sendLink);

        Link createLink = linkTo(methodOn(BasketController.class).createBasket(userId)).withRel("createOrder");
        basket.add(createLink);

        return ResponseEntity.ok(basket);
    }
    public ResponseEntity<Basket> sentOrderFallback(int userId, Throwable throwable)
    {
        Basket basket = basketService.sentOrderFallback(userId);
        return ResponseEntity.ok(basket);
    }

    @Operation(summary = "Get basket details for a user. The user has to be logged in to access his basket", responses = {
            @ApiResponse(responseCode = "200", description = "Basket details retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/myBasket/{userId}")
    public ResponseEntity<Basket> getBasket(@PathVariable @Parameter(description = "User ID", required = true) int userId)
    {
        Basket basket = basketService.getBasket(userId);

        Link sendLink = linkTo(methodOn(BasketController.class).sentOrder(userId)).withRel("sendOrder");
        basket.add(sendLink);

        Link getLink = linkTo(methodOn(BasketController.class).getBasket(userId)).withSelfRel();
        basket.add(getLink);
        return ResponseEntity.ok(basket);
    }

    @Operation(summary = "Add a book to the basket or increase the number of that book in basket", responses = {
            @ApiResponse(responseCode = "200", description = "Book added to the basket successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addBookToBasket/{bookId}/{basketId}")
    public  ResponseEntity<Basket> addBookToBasket(
            @PathVariable @Parameter(description = "Book ID", required = true) int bookId,
            @PathVariable @Parameter(description = "Basket ID", required = true) int basketId
    ) {
        Basket basket = basketService.addBookToBasket(bookId, basketId);
        Link decrementLink = linkTo(methodOn(BasketController.class).decrementBookFromBasket(bookId,basketId)).withRel("decrementBookFromBasket");
        basket.add(decrementLink);

        Link removeLink = linkTo(methodOn(BasketController.class).removeBookFromBasket(bookId,basketId)).withRel("removeBookFromBasket");
        basket.add(removeLink);

        Link addLink = linkTo(methodOn(BasketController.class).addBookToBasket(bookId,basketId)).withSelfRel();
        basket.add(addLink);

        return ResponseEntity.ok(basket);
    }

    @Operation(summary = "Remove a book from the basket. It removes all the copies of that book from basket", responses = {
            @ApiResponse(responseCode = "200", description = "Book removed from the basket successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/removeBookFromBasket/{bookId}/{basketId}")
    public ResponseEntity<Basket> removeBookFromBasket(
            @PathVariable @Parameter(description = "Book ID", required = true) int bookId,
            @PathVariable @Parameter(description = "Basket ID", required = true) int basketId) {
        Basket basket = basketService.removeBookFromBasket(bookId, basketId);
        Link decrementLink = linkTo(methodOn(BasketController.class).decrementBookFromBasket(bookId,basketId)).withRel("decrementBookFromBasket");
        basket.add(decrementLink);

        Link removeLink = linkTo(methodOn(BasketController.class).removeBookFromBasket(bookId,basketId)).withRel("removeBookFromBasket");
        basket.add(removeLink);

        Link addLink = linkTo(methodOn(BasketController.class).addBookToBasket(bookId,basketId)).withSelfRel();
        basket.add(addLink);
        return ResponseEntity.ok(basket);
    }

    @Operation(summary = "Decrement a book from the basket. It removes one copy of that book from basket", responses = {
            @ApiResponse(responseCode = "200", description = "Book decremented from the basket successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/decrementBookFromBasket/{bookId}/{basketId}")
    public ResponseEntity<Basket> decrementBookFromBasket(
            @PathVariable @Parameter(description = "Book ID", required = true) int bookId,
            @PathVariable  @Parameter(description = "Basket ID", required = true) int basketId
    ) {
        Basket basket = basketService.decrementBookFromBasket(bookId, basketId);
        Link decrementLink = linkTo(methodOn(BasketController.class).decrementBookFromBasket(bookId,basketId)).withRel("decrementBookFromBasket");
        basket.add(decrementLink);

        Link removeLink = linkTo(methodOn(BasketController.class).removeBookFromBasket(bookId,basketId)).withRel("removeBookFromBasket");
        basket.add(removeLink);

        Link addLink = linkTo(methodOn(BasketController.class).addBookToBasket(bookId,basketId)).withSelfRel();
        basket.add(addLink);
        return ResponseEntity.ok(basket);
    }
}
