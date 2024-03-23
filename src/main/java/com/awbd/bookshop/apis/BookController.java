package com.awbd.bookshop.apis;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.services.book.IBookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Book newBook) {
        return ResponseEntity.ok(bookService.addBook(token, newBook));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Book updateBook)  {
        return ResponseEntity.ok(bookService.updateBook(token, updateBook, id));
    }

    @PatchMapping("/addAuthorToBook/{bookId}")
    public ResponseEntity<Book> addAuthorToBook(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int bookId,
            @Valid @RequestBody Author newAuthor) {
        return ResponseEntity.ok(bookService.addAuthorToBook(token, bookId, newAuthor));
    }

    @PatchMapping("/addCategoriesToBook/{bookId}")
    public ResponseEntity<Book> addCategoriesToBook(
            @RequestHeader(name = "userToken") String token,
            @PathVariable int bookId,
            @Valid @RequestBody List<Category> newCategories) {
        return ResponseEntity.ok(bookService.addCategoriesToBook(token, bookId, newCategories));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token) {
        bookService.deleteBook(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllByAdmin")
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestHeader(name = "userToken") String token) {
        return ResponseEntity.ok(bookService.getBooks(token));
    }

    @GetMapping("/getAvailable")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @PathVariable String firstname,
            @PathVariable String lastName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(firstname, lastName));
    }

    @GetMapping("/getBooksByCategory/{category}")
    public ResponseEntity<List<Book>> getBooksByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    }
}
