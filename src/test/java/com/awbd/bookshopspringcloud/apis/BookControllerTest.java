//package com.awbd.bookshop.apis;
//
//import com.awbd.bookshop.dtos.RequestBook;
//import com.awbd.bookshop.mappers.BookMapper;
//import com.awbd.bookshop.models.*;
//import com.awbd.bookshop.services.basket.IBasketService;
//import com.awbd.bookshop.services.book.IBookService;
//import com.awbd.bookshop.services.category.ICategoryService;
//import com.awbd.bookshop.services.user.IUserService;
//import com.awbd.bookshop.services.user.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.jws.soap.SOAPBinding;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ActiveProfiles("mysql")
//public class BookControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private IBookService bookService;
//
//    @MockBean
//    private ICategoryService categoryService;
//
//    @MockBean
//    private IBasketService basketService;
//
//    @MockBean
//    private BookMapper mapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private IUserService userService;
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void save() throws Exception{
//        RequestBook book = new RequestBook("carte",20.3,2001,1,"serie");
//        when(bookService.addBook(mapper.requestBook(book))).thenReturn(new Book("carte",20.3,2001,1,"serie",false));
//        mockMvc.perform(post("/book")
//                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .param("name","carte")
//                        .param("price", String.valueOf(20.3))
//                        .param("year",String.valueOf(2001))
//                        .param("volume",String.valueOf(1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(mapper.requestBook(book)))
//        ) .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/book"));
//
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveErr() throws Exception{
//        RequestBook book = new RequestBook("carte",-1.0,2001,1,"serie");
//        mockMvc.perform(post("/book")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .param("name","carte")
//                        .param("price", String.valueOf(-1.0))
//                        .param("year",String.valueOf(2001))
//                        .param("volume",String.valueOf(1))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(mapper.requestBook(book)))
//                ) .andExpect(model().attributeExists("book"))
//                .andExpect(view().name("bookAddForm"));;
//
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addBook() throws Exception {
//        this.mockMvc.perform(get("/book/add")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("book"))
//                .andExpect(view().name("bookAddForm"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveBookUpdate() throws Exception{
//        int id = 1;
//        Book request = new Book("carte",20.3,2001,1,"serie",false);
//        request.setId(id);
//        Book book = new Book(id,"carte",20.3,2001,1,"serie",false);
//        when(bookService.updateBook(request, id)).thenReturn(book);
//
//        mockMvc.perform(post("/book/update")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .flashAttr("book",request)
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/book"));
//        verify(bookService,times(1)).updateBook(request,id);
//
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveBookUpdateErr() throws Exception{
//        int id = 1;
//        Book request = new Book("carte",-1,2001,1,"serie",false);
//        request.setId(id);
//        Book book = new Book(id,"carte",-1,2001,1,"serie",false);
//        mockMvc.perform(post("/book/update")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .flashAttr("book",request)
//                )
//                .andExpect(model().attributeExists("book"))
//                .andExpect(view().name("bookForm"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void updateBook() throws Exception {
//        int id = 1;
//        Book book = new Book(id,"carte",20.3,2001,1,"serie",false);
//        when(bookService.getBookById(id)).thenReturn(book);
//        mockMvc.perform(get("/book/update/{id}","1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("bookForm"))
//                .andExpect(model().attribute("book",book));
//        verify(bookService,times(1)).getBookById(id);
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addAuthBook() throws Exception {
//        int bookId = 1;
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);
//        when(bookService.addAuthorToBook(bookId,author)).thenReturn(book);
//
//        mockMvc.perform(post("/book/addAuthBook/{bookId}","1")
//                        .param("firstName","Lara")
//                        .param("lastName","Simon")
//                        .param("nationality","Romanian")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(author))
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/book"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addAuthBookErr() throws Exception {
//        int bookId = 1;
//        Author author = new Author(1,"","Simon","Romanian");
//        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);
//        when(bookService.getBookById(bookId)).thenReturn(book);
//
//        mockMvc.perform(post("/book/addAuthBook/{bookId}","1")
//                        .param("firstName","")
//                        .param("lastName","Simon")
//                        .param("nationality","Romanian")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(author))
//                )
//                .andExpect(model().attributeExists("book"))
//                .andExpect(view().name("bookAddAuthorToBook"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addAuthorToBook() throws Exception{
//        int bookId = 1;
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);
//
//        when(bookService.getBookById(bookId)).thenReturn(book);
//
//        this.mockMvc.perform(get("/book/addAuthorToBook/{bookId}","1")
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("author"))
//                .andExpect(model().attributeExists("book"))
//                .andExpect(view().name("bookAddAuthorToBook"));
//
//    }
//
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void showAddCategoriesToBookForm() throws Exception{
//        int bookId = 1;
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categoriesAll = new ArrayList<>();
//        categoriesAll.add(category1);
//        categoriesAll.add(category2);
//
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);
//
//        when(bookService.getBookById(bookId)).thenReturn(book);
//        when(categoryService.getCategories()).thenReturn(categoriesAll);
//
//        this.mockMvc.perform(get("/book/addCategBook/{bookId}","1")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("book",book))
//                .andExpect(model().attribute("categoriesAll",categoriesAll))
//                .andExpect(view().name("bookAddCategoriesToBook"));
//
//    }
//
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addCategoriesToBook() throws Exception {
//        int bookId = 1;
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categories = new ArrayList<>();
//        categories.add(category1);
//        categories.add(category2);
//
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author,categories);
//        when(bookService.addCategoriesToBook(bookId,book.getBookCategories())).thenReturn(book);
//
//        mockMvc.perform(post("/book/addCategoriesToBook/{bookId}","1")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book))
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/book"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void deleteBook() throws Exception{
//        int id=1;
//        mockMvc.perform(delete("/book/delete/{id}","1")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/book"));
//        verify(bookService).deleteBook(id);
//    }
//
//    @Test
//    public void getAllBooks() throws Exception {
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categories = new ArrayList<>();
//        categories.add(category1);
//        categories.add(category2);
//
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(1,"carte",20.3,2001,1,"serie",false,author,categories);
//
//        List<Book> books = new ArrayList<>();
//        books.add(book);
//
//        when(bookService.getBooks()).thenReturn(books);
//        mockMvc.perform(get("/book"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("bookList"))
//                .andExpect(model().attribute("books",books));
//    }
//
//    @Test
//    public void getAvailableBooksAnonymous() throws Exception {
//        Integer pageNo = 0;
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categoriesAll = new ArrayList<>();
//        categoriesAll.add(category1);
//        categoriesAll.add(category2);
//
//        when(categoryService.getCategories()).thenReturn(categoriesAll);
//        int noAvailableBooks = 1;
//        when(bookService.numberAvailableBooks()).thenReturn(noAvailableBooks);
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        Book book = new Book(1,"carte",20.3,2001,1,"serie",false,author,categoriesAll);
//
//        List<Book> books = new ArrayList<>();
//        books.add(book);
//
//        when(bookService.getAvailableBooks(pageNo,5)).thenReturn(books);
//        int currentPage = 1;
//        int userId = 0;
//
//        when(bookService.getBooks()).thenReturn(books);
//        mockMvc.perform(get("/book/getAvailable/0")
//                )
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("totalPages",1))
//                .andExpect(model().attribute("nrAvailable",noAvailableBooks))
//                .andExpect(model().attribute("books",books))
//                .andExpect(model().attribute("categoriesAll", categoriesAll))
//                .andExpect(view().name("bookAvailableListNoLogin"));
//    }
//
//        @Test
//        @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
//        public void getAvailableBooks() throws Exception {
//            Integer pageNo = 0;
//            Category category1 = new Category(1,"action");
//            Category category2 = new Category(2,"romance");
//
//            List<Category> categoriesAll = new ArrayList<>();
//            categoriesAll.add(category1);
//            categoriesAll.add(category2);
//
//            when(categoryService.getCategories()).thenReturn(categoriesAll);
//            int noAvailableBooks = 1;
//            when(bookService.numberAvailableBooks()).thenReturn(noAvailableBooks);
//            Author author = new Author(1,"Lara","Simon","Romanian");
//            Book book = new Book(1,"carte",20.3,2001,1,"serie",false,author,categoriesAll);
//
//            List<Book> books = new ArrayList<>();
//            books.add(book);
//
//            when(bookService.getAvailableBooks(pageNo,5)).thenReturn(books);
//
//            int currentPage = 1;
//            int userId = 3;
//            when(userService.getCurrentUserId()).thenReturn(userId);
//
//            User userul= new User("miruna","miruna@yahoo.com","pass","Miruna","Pos",true);
//            userul.setId(userId);
//
//            Basket basket = new Basket(3,false,51.0,userul);
//            when(basketService.getBasket(userId)).thenReturn(basket);
//            when(bookService.getBooks()).thenReturn(books);
//            mockMvc.perform(get("/book/getAvailable/0")
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(model().attribute("totalPages",1))
//                    .andExpect(model().attribute("nrAvailable",noAvailableBooks))
//                    .andExpect(view().name("bookAvailableList"))
//                   .andExpect(model().attribute("categoriesAll", categoriesAll))
//                    .andExpect(model().attribute("books",books))
//                    .andExpect(model().attribute("basket",basket));
//        }
//
//    @Test
//    public void getBooksByCategory() throws Exception{
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categoriesAll = new ArrayList<>();
//        categoriesAll.add(category1);
//        categoriesAll.add(category2);
//        Author author1 = new Author(1,"Lara","Simoni","Romanian");
//        when(categoryService.getCategories()).thenReturn(categoriesAll);
//
//        Book book = new Book(1,"carte",20.0,2001,1,"serie",false,author1,categoriesAll);
//        List<Book> books = new ArrayList<>();
//        books.add(book);
//        when(bookService.getBooksByCategory("action")).thenReturn(books);
//
//        mockMvc.perform(get("/book/getBooksByCategory")
//                        .param("selectedCategory","action"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("booksByCateg"))
//                .andExpect(model().attribute("categoriesAll",categoriesAll))
//                .andExpect(model().attribute("books",books))
//                .andExpect(model().attribute("selectedCategory","action"));
//
//    }
//
//    @Test
//    public void getBooksByAuthor() throws Exception{
//        String firstName = "Lara";
//        String lastName = "Simoni";
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categoriesAll = new ArrayList<>();
//        categoriesAll.add(category1);
//        categoriesAll.add(category2);
//        Author author1 = new Author(1,firstName,lastName,"Romanian");
//
//        Book book = new Book(1,"carte",20.0,2001,1,"serie",false,author1,categoriesAll);
//        List<Book> books = new ArrayList<>();
//        books.add(book);
//
//        when(bookService.getBooksByAuthor(firstName,lastName)).thenReturn(books);
//        when(categoryService.getCategories()).thenReturn(categoriesAll);
//
//
//        mockMvc.perform(get("/book/getBooksByAuthor/{firstname}/{lastName}","Lara","Simoni")
//                        )
//                .andExpect(status().isOk())
//                .andExpect(view().name("booksByAuthor"))
//                .andExpect(model().attribute("categoriesAll",categoriesAll))
//                .andExpect(model().attribute("books",books))
//                .andExpect(model().attribute("firstname","Lara"))
//                .andExpect(model().attribute("lastname","Simoni"));
//
//    }
//
//}