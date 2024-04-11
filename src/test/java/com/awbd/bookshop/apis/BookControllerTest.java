package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.RequestBook;
import com.awbd.bookshop.mappers.BookMapper;
import com.awbd.bookshop.models.*;
import com.awbd.bookshop.services.basket.IBasketService;
import com.awbd.bookshop.services.book.IBookService;
import com.awbd.bookshop.services.category.ICategoryService;
import com.awbd.bookshop.services.user.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jws.soap.SOAPBinding;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
@Profile("mysql")
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBookService bookService;

    @MockBean
    private ICategoryService categoryService;

    @MockBean
    private IBasketService basketService;

    @MockBean
    private BookMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    //  @PostMapping("")
    //    public ModelAndView save(
    //            @Valid @ModelAttribute RequestBook newBook){
    //        bookService.addBook(mapper.requestBook(newBook));
    //        return new ModelAndView("redirect:/book");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void save() throws Exception{
        RequestBook book = new RequestBook("carte",20.3,2001,1,"serie");
        when(bookService.addBook(mapper.requestBook(book))).thenReturn(new Book("carte",20.3,2001,1,"serie",false));
        mockMvc.perform(post("/book")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name","carte")
                        .param("price", String.valueOf(20.3))
                        .param("year",String.valueOf(2001))
                        .param("volume",String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mapper.requestBook(book)))
        ) .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

    }
    //  @RequestMapping("/add")
    //    public ModelAndView addBook(Model model){
    //        model.addAttribute("book",new Book());
    //        return new ModelAndView("bookAddForm");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addBook() throws Exception {
        this.mockMvc.perform(get("/book/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("bookAddForm"));
    }

    //    @PostMapping("/update")
    //    public ModelAndView saveBookUpdate(
    //            @Valid @ModelAttribute Book book){
    //        bookService.updateBook(book,book.getId());
    //        return new ModelAndView("redirect:/book");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void saveBookUpdate() throws Exception{
        int id = 1;
        Book request = new Book("carte",20.3,2001,1,"serie",false);
        request.setId(id);
        Book book = new Book(id,"carte",20.3,2001,1,"serie",false);
        when(bookService.updateBook(request, id)).thenReturn(book);

        mockMvc.perform(post("/book/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("book",request)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
        verify(bookService,times(1)).updateBook(request,id);

    }

    // @RequestMapping("/update/{id}") //cand merg pe ruta asta doar se afiseaza categoryForm
    //    public ModelAndView updateBook(
    //            @PathVariable int id,
    //            @Valid Model model){
    //
    //        model.addAttribute("book",bookService.getBookById(id));
    //        return new ModelAndView("bookForm");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void updateAuthor() throws Exception {
        int id = 1;
        Book book = new Book(id,"carte",20.3,2001,1,"serie",false);
        when(bookService.getBookById(id)).thenReturn(book);
        mockMvc.perform(get("/book/update/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookForm"))
                .andExpect(model().attribute("book",book));
        verify(bookService,times(1)).getBookById(id);
    }

    //  @PostMapping("/addAuthBook/{bookId}")
    //    public ModelAndView addAuthBook(
    //            @PathVariable int bookId,
    //            @Valid @ModelAttribute Author author){
    //        bookService.addAuthorToBook(bookId,author);
    //        return new ModelAndView("redirect:/book");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addAuthBook() throws Exception {
        int bookId = 1;
        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);
        when(bookService.addAuthorToBook(bookId,author)).thenReturn(book);

        mockMvc.perform(post("/book/addAuthBook/{bookId}","1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    // @RequestMapping("/addAuthorToBook/{bookId}")
    //    public ModelAndView addAuthorToBook(
    //            @PathVariable int bookId,
    //            @Valid Model model,
    //            Author author) {
    //        Book book = bookService.getBookById(bookId);
    //        if (book != null && book.getAuthor() != null) {
    //            model.addAttribute("author", book.getAuthor());
    //        }
    //        model.addAttribute("book",bookService.getBookById(bookId));
    //        //model.addAttribute("author",author);
    //        return new ModelAndView("bookAddAuthorToBook");
    //
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addAuthorToBook() throws Exception{
        int bookId = 1;
        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);

        when(bookService.getBookById(bookId)).thenReturn(book);

        this.mockMvc.perform(get("/book/addAuthorToBook/{bookId}","1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("bookAddAuthorToBook"));

    }

    //    @RequestMapping("/addCategBook/{bookId}")
    //    public ModelAndView showAddCategoriesToBookForm(@PathVariable int bookId, Model model) {
    //        model.addAttribute("book",bookService.getBookById(bookId));
    //        List<Category> categoriesAll = categoryService.getCategories();
    //
    //        model.addAttribute("categoriesAll", categoriesAll);
    //        return new ModelAndView("bookAddCategoriesToBook");
    //    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void showAddCategoriesToBookForm() throws Exception{
        int bookId = 1;
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);

        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author);

        when(bookService.getBookById(bookId)).thenReturn(book);
        when(categoryService.getCategories()).thenReturn(categoriesAll);

        this.mockMvc.perform(get("/book/addCategBook/{bookId}","1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book",book))
                .andExpect(model().attribute("categoriesAll",categoriesAll))
                .andExpect(view().name("bookAddCategoriesToBook"));

    }

    //@PostMapping("/addCategoriesToBook/{bookId}")
    //    public ModelAndView addCategoriesToBook(
    //            @PathVariable int bookId,
    //            @ModelAttribute Book book
    //            ){
    //
    //        bookService.addCategoriesToBook(bookId, book.getBookCategories());
    //        return new ModelAndView("redirect:/book");
    //    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addCategoriesToBook() throws Exception {
        int bookId = 1;
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(bookId,"carte",20.3,2001,1,"serie",false,author,categories);
        when(bookService.addCategoriesToBook(bookId,book.getBookCategories())).thenReturn(book);

        mockMvc.perform(post("/book/addCategoriesToBook/{bookId}","1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    // @RequestMapping("/delete/{id}")
    //    public ModelAndView deleteBook(
    //            @PathVariable int id
    //    ){
    //        bookService.deleteBook(id);
    //        return new ModelAndView("redirect:/book");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void deleteBook() throws Exception{
        int id=1;
        mockMvc.perform(delete("/book/delete/{id}","1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
        verify(bookService).deleteBook(id);
    }

    //    @RequestMapping("")
    //    public ModelAndView getAllBooks(Model model){
    //        List<Book> books = bookService.getBooks();
    //        model.addAttribute("books",books);
    //        return new ModelAndView ("bookList");
    //    }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void getAllBooks() throws Exception {
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(1,"carte",20.3,2001,1,"serie",false,author,categories);

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookService.getBooks()).thenReturn(books);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookList"))
                .andExpect(model().attribute("books",books));
    }

//     @RequestMapping("/getAvailable/{pageNo}")
//        public ModelAndView getAvailableBooks(Model model,
//                                              @PathVariable Integer pageNo
//                                              ){
//            List<Category> categoriesAll = categoryService.getCategories();
//            model.addAttribute("categoriesAll", categoriesAll);
//            List<Book> books = bookService.getAvailableBooks(pageNo, 5);
//            model.addAttribute("books",books);
//            int userId = getCurrentUserId();
//            Basket basket = basketService.getBasket(userId);
//            model.addAttribute("basket",basket);
//            model.addAttribute(pageNo);
//            model.addAttribute("totalPages",(int) Math.ceil((double) books.size() / 5));
//            int currentPage = pageNo > 0 ? pageNo : 0;
//            model.addAttribute(currentPage);
//            return new ModelAndView ("bookAvailableList");
//        }

    //Exception processing template "bookAvailableList":
    //Exception evaluating SpringEL expression: "basket.id" (template: "bookAvailableList" - line 105, col 29)
   /* @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void getAvailableBooks() throws Exception {
        Integer pageNo = 0;
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);

        when(categoryService.getCategories()).thenReturn(categoriesAll);

        Author author = new Author(1,"Lara","Simon","Romanian");
        Book book = new Book(1,"carte",20.3,2001,1,"serie",false,author,categoriesAll);

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookService.getAvailableBooks(pageNo,5)).thenReturn(books);

        int userId = 1;
        User user = new User("miruna","miruna@yahoo.com","pass","miruna","pos",true);
        user.setId(userId);
        Basket basket = new Basket(1,false,0.0,user);
        when(basketService.getBasket(userId)).thenReturn(basket);

        when(bookService.getBooks()).thenReturn(books);
        mockMvc.perform(get("/book/getAvailable/{pageNo}","0")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bookAvailableList"))
                .andExpect(model().attribute("categoriesAll", categoriesAll))
                .andExpect(model().attribute("books",books))
                .andExpect(model().attribute("basket",basket))
                .andExpect(model().attributeExists("totalPages"));
    }*/

    // @GetMapping("/getBooksByCategory")
    //    public ModelAndView getBooksByCategory(
    //            @RequestParam(name = "selectedCategory") String category,
    //            Model model) {
    ////        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    //        List<Category> categoriesAll = categoryService.getCategories();
    //        model.addAttribute("categoriesAll", categoriesAll);
    //        List<Book> books = bookService.getBooksByCategory(category);
    //        model.addAttribute("books",books);
    //        model.addAttribute("selectedCategory", category);
    //        return new ModelAndView("booksByCateg");
    //    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void getBooksByCategory() throws Exception{
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);
        Author author1 = new Author(1,"Lara","Simoni","Romanian");
        when(categoryService.getCategories()).thenReturn(categoriesAll);

        Book book = new Book(1,"carte",20.0,2001,1,"serie",false,author1,categoriesAll);
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookService.getBooksByCategory("action")).thenReturn(books);

        mockMvc.perform(get("/book/getBooksByCategory")
                        .param("selectedCategory","action"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksByCateg"))
                .andExpect(model().attribute("categoriesAll",categoriesAll))
                .andExpect(model().attribute("books",books))
                .andExpect(model().attribute("selectedCategory","action"));

    }

    // @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
    //    public ModelAndView getBooksByAuthor(
    //            @PathVariable String firstname,
    //            @PathVariable String lastName,
    //            Model model) {
    ////        return ResponseEntity.ok(bookService.getBooksByAuthor(firstname, lastName));
    //        List<Book> books = bookService.getBooksByAuthor(firstname, lastName);
    //        model.addAttribute("books",books);
    //        List<Category> categoriesAll = categoryService.getCategories();
    //        model.addAttribute("categoriesAll", categoriesAll);
    //        model.addAttribute("firstname",firstname);
    //        model.addAttribute("lastname",lastName);
    //        return new ModelAndView("booksByAuthor");
    //    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void getBooksByAuthor() throws Exception{
        String firstName = "Lara";
        String lastName = "Simoni";
        Category category1 = new Category(1,"action");
        Category category2 = new Category(2,"romance");

        List<Category> categoriesAll = new ArrayList<>();
        categoriesAll.add(category1);
        categoriesAll.add(category2);
        Author author1 = new Author(1,firstName,lastName,"Romanian");

        Book book = new Book(1,"carte",20.0,2001,1,"serie",false,author1,categoriesAll);
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookService.getBooksByAuthor(firstName,lastName)).thenReturn(books);
        when(categoryService.getCategories()).thenReturn(categoriesAll);


        mockMvc.perform(get("/book/getBooksByAuthor/{firstname}/{lastName}","Lara","Simoni")
                        )
                .andExpect(status().isOk())
                .andExpect(view().name("booksByAuthor"))
                .andExpect(model().attribute("categoriesAll",categoriesAll))
                .andExpect(model().attribute("books",books))
                .andExpect(model().attribute("firstname","Lara"))
                .andExpect(model().attribute("lastname","Simoni"));

    }

}
