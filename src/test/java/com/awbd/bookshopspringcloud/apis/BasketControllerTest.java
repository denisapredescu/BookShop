package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.dtos.BookFromBasketDetails;
import com.awbd.bookshopspringcloud.mappers.BasketMapper;
import com.awbd.bookshopspringcloud.models.*;
import com.awbd.bookshopspringcloud.services.basket.IBasketService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
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
    @MockBean
    private IUserService userService;

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER","ADMIN"})
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

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER","ADMIN"})
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

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER","ADMIN"})
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

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER","ADMIN"})
    public void sentOrder() throws Exception{
        int userId = 3;
        when(userService.getCurrentUserId()).thenReturn(userId);

        User userul= new User("miruna","miruna@yahoo.com","pass","Miruna","Pos",true);
        userul.setId(userId);
        Basket basket = new Basket(3,false,51.0,userul);
        when(basketService.sentOrder(userId)).thenReturn(basket);

        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");
        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);
        Author author = new Author(1,"Lara","Simon","Romanian");
        BookFromBasketDetails book = new BookFromBasketDetails("carte",51.0,1,3);
        List<BookFromBasketDetails> books = new ArrayList<>();
        books.add(book);

        when(basketService.findBooksFromCurrentBasket(basket.getId())).thenReturn(books);

        mockMvc.perform(get("/basket/sentOrder")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket/myBasket"));
    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER","ADMIN"})
    public void getBasket() throws Exception{
        int userId = 3;
        when(userService.getCurrentUserId()).thenReturn(userId);

        User userul= new User("miruna","miruna@yahoo.com","pass","Miruna","Pos",true);
        userul.setId(userId);
        Basket basket = new Basket(3,false,51.0,userul);
        when(basketService.getBasket(userId)).thenReturn(basket);

        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");
        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);
        Author author = new Author(1,"Lara","Simon","Romanian");
        BookFromBasketDetails book = new BookFromBasketDetails("carte",51.0,1,3);
        List<BookFromBasketDetails> books = new ArrayList<>();
        books.add(book);

        when(basketService.findBooksFromCurrentBasket(basket.getId())).thenReturn(books);

        mockMvc.perform(get("/basket/myBasket")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("basket",basket))
                .andExpect(model().attribute("books",books))
                .andExpect(view().name("basketView"));
    }

}
