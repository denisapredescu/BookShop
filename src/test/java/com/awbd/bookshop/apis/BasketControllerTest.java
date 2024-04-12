package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.mappers.BasketMapper;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.services.basket.IBasketService;
import com.awbd.bookshop.services.user.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("mysql")
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBasketService basketService;

    @MockBean
    private BasketMapper mapper;

    // @RequestMapping("/addBookToBasket/{bookId}/{basketId}")
    //    public  ModelAndView addBookToBasket(
    //            @PathVariable int bookId,
    //            @PathVariable int basketId,
    //            Model model
    //    ) {
    //        Basket basket = basketService.addBookToBasket(bookId, basketId);
    //        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
    //        model.addAttribute(basket);
    //        model.addAttribute(books);
    //        return new ModelAndView("redirect:/basket/myBasket");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addBookToBasket() throws Exception{
        int bookId = 1;
        int basketId = 1;

        Basket basket = new Basket(basketId,false,0.0,new User("miruna","miruna@yahoo.com","pass"));

        when(basketService.addBookToBasket(bookId,basketId)).thenReturn(basket);
        BookFromBasketDetails book = new BookFromBasketDetails("name",10.0,1,1);
        List<BookFromBasketDetails> books = new ArrayList<>();
        books.add(book);

        when(basketService.findBooksFromCurrentBasket(basketId)).thenReturn(books);

        mockMvc.perform(get("/basket/addBookToBasket/{bookId}/{basketId}","1","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket/myBasket"));

        verify(basketService).addBookToBasket(bookId,basketId);
        verify(basketService).findBooksFromCurrentBasket(basketId);
    }

    // @RequestMapping("/removeBookFromBasket/{bookId}/{basketId}")
    //    public  ModelAndView removeBookFromBasket(
    //            @PathVariable int bookId,
    //            @PathVariable int basketId,
    //            Model model) {
    //        Basket basket = basketService.removeBookFromBasket(bookId, basketId);
    //        model.addAttribute("basket",basket);
    //        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
    //        model.addAttribute("books",books);
    //        return new ModelAndView("redirect:/basket/myBasket");
    //    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void removeBookFromBasket() throws Exception {
        int bookId = 1;
        int basketId = 1;

        Basket basket = new Basket(basketId,false,0.0,new User("miruna","miruna@yahoo.com","pass"));

        when(basketService.removeBookFromBasket(bookId,basketId)).thenReturn(basket);
        BookFromBasketDetails book = new BookFromBasketDetails("name",10.0,1,1);
        List<BookFromBasketDetails> books = new ArrayList<>();
        books.add(book);

        when(basketService.findBooksFromCurrentBasket(basketId)).thenReturn(books);

        mockMvc.perform(get("/basket/removeBookFromBasket/{bookId}/{basketId}","1","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket/myBasket"));

        verify(basketService).removeBookFromBasket(bookId,basketId);
        verify(basketService).findBooksFromCurrentBasket(basketId);
    }

    // @RequestMapping("/decrementBookFromBasket/{bookId}/{basketId}")
    //    public ModelAndView decrementBookFromBasket(
    //            @PathVariable int bookId,
    //            @PathVariable int basketId,
    //            Model model
    //    ) {
    //        Basket basket = basketService.decrementBookFromBasket(bookId, basketId);
    //        model.addAttribute(basket);
    //        List<BookFromBasketDetails> books = basketService.findBooksFromCurrentBasket(basketId);
    //        model.addAttribute(books);
    //        return new ModelAndView("redirect:/basket/myBasket");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void decrementBookFromBasket() throws Exception {
        int bookId = 1;
        int basketId = 1;

        Basket basket = new Basket(basketId,false,10.0,new User("miruna","miruna@yahoo.com","pass"));

        when(basketService.decrementBookFromBasket(bookId,basketId)).thenReturn(basket);
        BookFromBasketDetails book = new BookFromBasketDetails("name",10.0,1,1);
        List<BookFromBasketDetails> books = new ArrayList<>();
        books.add(book);

        when(basketService.findBooksFromCurrentBasket(basketId)).thenReturn(books);

        mockMvc.perform(get("/basket/decrementBookFromBasket/{bookId}/{basketId}","1","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket/myBasket"));

        verify(basketService).decrementBookFromBasket(bookId,basketId);
        verify(basketService).findBooksFromCurrentBasket(basketId);
    }

}
