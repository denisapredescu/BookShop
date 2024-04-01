package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.RequestBook;
import com.awbd.bookshop.mappers.BookMapper;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.services.basket.IBasketService;
import com.awbd.bookshop.services.book.IBookService;
import com.awbd.bookshop.services.category.ICategoryService;
import com.awbd.bookshop.services.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    final IBookService bookService;
    final BookMapper mapper;
    final ICategoryService categoryService;
    final IBasketService basketService;
    final IUserService userService;

    public BookController(IBookService bookService, BookMapper mapper, ICategoryService categoryService, IBasketService basketService, IUserService userService) {
        this.bookService = bookService;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.basketService = basketService;
        this.userService = userService;
    }

//    @PostMapping("/add")
//    public ResponseEntity<Book> addBook(
//            @Valid @RequestBody RequestBook newBook) {
//        return ResponseEntity.ok(
//                bookService.addBook(mapper.requestBook(newBook))
//        );
//    }
    @PostMapping("")
    public ModelAndView save(
            @Valid @ModelAttribute RequestBook newBook){
        bookService.addBook(mapper.requestBook(newBook));
        return new ModelAndView("redirect:/book");
    }
    @RequestMapping("/add")
    public ModelAndView addBook(
            @Valid Model model,
            Book newBook){
        model.addAttribute("book",newBook);
        return new ModelAndView("bookAddForm");
    }

//    @PatchMapping("/update/{id}")
//    public ResponseEntity<BookResponse> updateBook(
//            @PathVariable int id,
//            @Valid @RequestBody RequestBook updateBook)  {
//        return ResponseEntity.ok(
//            mapper.bookDto(bookService.updateBook(mapper.requestBook(updateBook), id))
//        );
//    }
    @PostMapping("/update")
    public ModelAndView saveBookUpdate(
            @Valid @ModelAttribute Book book){
        bookService.updateBook(book,book.getId());
        return new ModelAndView("redirect:/book");
    }
    @RequestMapping("/update/{id}") //cand merg pe ruta asta doar se afiseaza categoryForm
    public ModelAndView updateBook(
            @PathVariable int id,
            @Valid Model model){

        model.addAttribute("book",bookService.getBookById(id));
        return new ModelAndView("bookForm");
    }

//    @PatchMapping("/addAuthorToBook/{bookId}")
//    public ResponseEntity<BookResponse> addAuthorToBook(
//            @PathVariable int bookId,
//            @Valid @RequestBody Author newAuthor) {
//        return ResponseEntity.ok(
//                mapper.bookDto(bookService.addAuthorToBook(bookId, newAuthor))
//        );
//    }
    @PostMapping("/addAuthBook/{bookId}")
    public ModelAndView addAuthBook(
            @PathVariable int bookId,
            @Valid @ModelAttribute Author author){
        bookService.addAuthorToBook(bookId,author);
        return new ModelAndView("redirect:/book");
    }
    @RequestMapping("/addAuthorToBook/{bookId}")
    public ModelAndView addAuthorToBook(
            @PathVariable int bookId,
            @Valid Model model,
            Author author) {
        Book book = bookService.getBookById(bookId);
        if (book != null && book.getAuthor() != null) {
            model.addAttribute("author", book.getAuthor());
        }
        model.addAttribute("book",bookService.getBookById(bookId));
        //model.addAttribute("author",author);
        return new ModelAndView("bookAddAuthorToBook");

    }

//    @PatchMapping("/addCategoriesToBook/{bookId}")
//    public ResponseEntity<BookResponse> addCategoriesToBook(
//            @PathVariable int bookId,
//            @Valid @RequestBody List<Category> newCategories) {
//        return ResponseEntity.ok(
//                mapper.bookDto(bookService.addCategoriesToBook(bookId, newCategories))
//        );
//    }
    @RequestMapping("/addCategBook/{bookId}")
    public ModelAndView showAddCategoriesToBookForm(@PathVariable int bookId, Model model) {
        model.addAttribute("book",bookService.getBookById(bookId));
        List<Category> categoriesAll = categoryService.getCategories();

        model.addAttribute("categoriesAll", categoriesAll);
        return new ModelAndView("bookAddCategoriesToBook");
    }

    @PostMapping("/addCategoriesToBook/{bookId}")
    public ModelAndView addCategoriesToBook(
            @PathVariable int bookId,
            @ModelAttribute Book book
            ){

        bookService.addCategoriesToBook(bookId, book.getBookCategories());
        return new ModelAndView("redirect:/book");
    }

//    @PatchMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteBook(
//            @PathVariable int id) {
//        bookService.deleteBook(id);
//        return ResponseEntity.noContent().build();
//    }
    @RequestMapping("/delete/{id}")
    public ModelAndView deleteBook(
            @PathVariable int id
    ){
        bookService.deleteBook(id);
        return new ModelAndView("redirect:/book");
    }

//    @GetMapping("/getAllByAdmin")
//    public ResponseEntity<List<BookResponse>> getAllBooks() {
//        List<Book> books = bookService.getBooks();
//        return ResponseEntity.ok(
//            books.stream().map(
//                mapper::bookDto
//            ).toList()
//        );
//    }

    @RequestMapping("")
    public ModelAndView getAllBooks(Model model){
        List<Book> books = bookService.getBooks();
        model.addAttribute("books",books);
        return new ModelAndView ("bookList");
    }

//    @GetMapping("/getAvailable")
//    public ResponseEntity<List<BookResponse>> getAvailableBooks(){
//        List<Book> books = bookService.getAvailableBooks();
//        return ResponseEntity.ok(
//                books.stream().map(
//                        mapper::bookDto
//                ).toList()
//        );
//    }
    @RequestMapping("/getAvailable")
    public ModelAndView getAvailableBooks(Model model){
        List<Category> categoriesAll = categoryService.getCategories();
        model.addAttribute("categoriesAll", categoriesAll);
        List<Book> books = bookService.getAvailableBooks();
        model.addAttribute("books",books);
        int userId = getCurrentUserId();
        Basket basket = basketService.getBasket(userId);
        model.addAttribute("basket",basket);

        return new ModelAndView ("bookAvailableList");
    }
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return userService.getId(authentication.getName());
        }
        return 0;
    }
//    @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
//    public ResponseEntity<List<BookResponse>> getBooksByAuthor(
//            @PathVariable String firstname,
//            @PathVariable String lastName) {
////        return ResponseEntity.ok(bookService.getBooksByAuthor(firstname, lastName));
//        List<Book> books = bookService.getBooksByAuthor(firstname, lastName);
//        return ResponseEntity.ok(
//                books.stream().map(
//                        mapper::bookDto
//                ).toList()
//        );
//    }


//    @GetMapping("/getBooksByCategory/{category}")
//    public ResponseEntity<List<BookResponse>> getBooksByCategory(
//            @PathVariable String category) {
////        return ResponseEntity.ok(bookService.getBooksByCategory(category));
//        List<Book> books = bookService.getBooksByCategory(category);
//        return ResponseEntity.ok(
//                books.stream().map(
//                        mapper::bookDto
//                ).toList()
//        );
//    }
    @GetMapping("/getBooksByCategory")
    public ModelAndView getBooksByCategory(
            @RequestParam(name = "selectedCategory") String category,
            Model model) {
//        return ResponseEntity.ok(bookService.getBooksByCategory(category));
        List<Category> categoriesAll = categoryService.getCategories();
        model.addAttribute("categoriesAll", categoriesAll);
        List<Book> books = bookService.getBooksByCategory(category);
        model.addAttribute("books",books);
        model.addAttribute("selectedCategory", category);
        return new ModelAndView("booksByCateg");
    }

    @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
    public ModelAndView getBooksByAuthor(
            @PathVariable String firstname,
            @PathVariable String lastName,
            Model model) {
//        return ResponseEntity.ok(bookService.getBooksByAuthor(firstname, lastName));
        List<Book> books = bookService.getBooksByAuthor(firstname, lastName);
        model.addAttribute("books",books);
        List<Category> categoriesAll = categoryService.getCategories();
        model.addAttribute("categoriesAll", categoriesAll);
        model.addAttribute("firstname",firstname);
        model.addAttribute("lastname",lastName);
        return new ModelAndView("booksByAuthor");
    }

}
