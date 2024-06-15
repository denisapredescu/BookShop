package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.dtos.RequestBook;
import com.awbd.bookshopspringcloud.mappers.BookMapper;
import com.awbd.bookshopspringcloud.models.Basket;
import com.awbd.bookshopspringcloud.models.Book;
import com.awbd.bookshopspringcloud.services.basket.IBasketService;
import com.awbd.bookshopspringcloud.services.book.IBookService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    final IBookService bookService;
    final BookMapper mapper;
    final IBasketService basketService;
    final IUserService userService;
    public BookController(IBookService bookService, BookMapper mapper, IBasketService basketService, IUserService userService) {
        this.bookService = bookService;
        this.mapper = mapper;
        this.basketService = basketService;
        this.userService = userService;
    }

    @PostMapping("")
    public ModelAndView save(
            @Valid @ModelAttribute("book") RequestBook newBook,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book",newBook);
            return new ModelAndView("bookAddForm");}

        bookService.addBook(mapper.requestBook(newBook));

        return new ModelAndView("redirect:/book");
    }

    @RequestMapping("/add")
    public ModelAndView addBook(Model model){
        model.addAttribute("book",new Book());
        return new ModelAndView("bookAddForm");
    }

    @PostMapping("/update")
    public ModelAndView saveBookUpdate(
            @Valid @ModelAttribute("book") Book book,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book",book);
            return new ModelAndView("bookForm");}
        bookService.updateBook(book,book.getId());
        return new ModelAndView("redirect:/book");
    }
    @RequestMapping("/update/{id}")
    public ModelAndView updateBook(
            @PathVariable int id,
            Model model){

        model.addAttribute("book",bookService.getBookById(id));
        return new ModelAndView("bookForm");
    }

//    @PostMapping("/addAuthBook/{bookId}")
//    public ModelAndView addAuthBook(
//            @PathVariable int bookId,
//            @Valid @ModelAttribute("author") Author author,
//            BindingResult bindingResult,
//            Model model){
//        if(bindingResult.hasErrors()){
//            Book book = bookService.getBookById(bookId);
//            if (book != null && book.getAuthor() != null) {
//                model.addAttribute("author", book.getAuthor());
//            }
//            model.addAttribute("book",bookService.getBookById(bookId));
//            return new ModelAndView("bookAddAuthorToBook");
//        }
//
//        bookService.addAuthorToBook(bookId,author);
//        return new ModelAndView("redirect:/book");
//    }
//    @RequestMapping("/addAuthorToBook/{bookId}")
//    public ModelAndView addAuthorToBook(
//            @PathVariable int bookId,
//            @Valid Model model,
//            Author author) {
//        Book book = bookService.getBookById(bookId);
//        if (book != null && book.getAuthor() != null) {
//            model.addAttribute("author", book.getAuthor());
//        }
//        model.addAttribute("book",bookService.getBookById(bookId));
//        return new ModelAndView("bookAddAuthorToBook");
//
//    }

//    @RequestMapping("/addCategBook/{bookId}")
//    public ModelAndView showAddCategoriesToBookForm(@PathVariable int bookId, Model model) {
//        model.addAttribute("book",bookService.getBookById(bookId));
//        List<Category> categoriesAll = categoryService.getCategories();
//
//        model.addAttribute("categoriesAll", categoriesAll);
//        return new ModelAndView("bookAddCategoriesToBook");
//    }

//    @PostMapping("/addCategoriesToBook/{bookId}")
//    public ModelAndView addCategoriesToBook(
//            @PathVariable int bookId,
//            @ModelAttribute Book book
//            ){
//
//        bookService.addCategoriesToBook(bookId, book.getBookCategories());
//        return new ModelAndView("redirect:/book");
//    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteBook(
            @PathVariable int id
    ){
        bookService.deleteBook(id);
        return new ModelAndView("redirect:/book");
    }

    @RequestMapping("")
    public ModelAndView getAllBooks(Model model){
        List<Book> books = bookService.getBooks();
        model.addAttribute("books",books);
        return new ModelAndView ("bookList");
    }

    @RequestMapping("/getAvailable/{pageNo}")
   // @RequestMapping("/getAvailable")
    public ModelAndView getAvailableBooks(Model model,
                                          @PathVariable Integer pageNo
                                          ){
        //List<Category> categoriesAll = categoryService.getCategories();
        List<String> categoriesAll = bookService.getAllCategories();
        List<String> allDistinctCategories = categoriesAll.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .distinct()
                .toList();

        model.addAttribute("categoriesAll", allDistinctCategories);

        //List<Book> books = bookService.getAllAvailableBooks();
        List<Book> books = bookService.getAvailableBooks(pageNo, 5);
        model.addAttribute("books",books);
        model.addAttribute(pageNo);
        int noAvailableBooks = bookService.numberAvailableBooks();
        model.addAttribute("nrAvailable",noAvailableBooks);
        if(noAvailableBooks<=5)
           model.addAttribute("totalPages",1);
        else
            model.addAttribute("totalPages",(int) Math.ceil((double) noAvailableBooks / 5));
        int currentPage = pageNo > 0 ? pageNo : 0;
        model.addAttribute(currentPage);

        int userId = userService.getCurrentUserId();

       if(userId==0)
           return new ModelAndView("bookAvailableListNoLogin");
        else
       {
            Basket basket = basketService.getBasket(userId);
            model.addAttribute("basket",basket);
            return new ModelAndView ("bookAvailableList");
       }
    }

    @GetMapping("/getBooksByCategory")
    public ModelAndView getBooksByCategory(
            @RequestParam(name = "selectedCategory") String category,
            Model model) {
        //List<Category> categoriesAll = categoryService.getCategories();
        List<String> categoriesAll = bookService.getAllCategories();
        List<String> allDistinctCategories = categoriesAll.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .distinct()
                .toList();

        model.addAttribute("categoriesAll", allDistinctCategories);
        List<Book> books = bookService.getBooksByCategory(category);
        model.addAttribute("books",books);
        model.addAttribute("selectedCategory", category);
        return new ModelAndView("booksByCateg");
    }

//    @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
//    public ModelAndView getBooksByAuthor(
//            @PathVariable String firstname,
//            @PathVariable String lastName,
//            Model model) {
//        List<Book> books = bookService.getBooksByAuthor(firstname, lastName);
//        model.addAttribute("books",books);
//        List<Category> categoriesAll = categoryService.getCategories();
//        model.addAttribute("categoriesAll", categoriesAll);
//        model.addAttribute("firstname",firstname);
//        model.addAttribute("lastname",lastName);
//        return new ModelAndView("booksByAuthor");
//    }
@GetMapping("/getBooksByAuthor/{authorName}")
public ModelAndView getBooksByAuthor(
        @PathVariable String authorName,
        Model model) {
    List<Book> books = bookService.getBooksByAuthor(authorName);
    model.addAttribute("books",books);
    //List<Category> categoriesAll = categoryService.getCategories();
    List<String> categoriesAll = bookService.getAllCategories();
    List<String> allDistinctCategories = categoriesAll.stream()
            .flatMap(s -> Arrays.stream(s.split(",")))
            .distinct()
            .toList();

    model.addAttribute("categoriesAll", allDistinctCategories);
    model.addAttribute("authorName",authorName);
    return new ModelAndView("booksByAuthor");
}

}
