package com.awbd.bookshop.apis;

import com.awbd.bookshop.dtos.BookResponse;
import com.awbd.bookshop.dtos.RequestBook;
import com.awbd.bookshop.mappers.BookMapper;
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
    final IBookService bookService;
    final BookMapper mapper;

    public BookController(IBookService bookService, BookMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @Valid @RequestBody RequestBook newBook) {
        return ResponseEntity.ok(
                bookService.addBook(mapper.requestBook(newBook))
        );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable int id,
            @Valid @RequestBody RequestBook updateBook)  {
        return ResponseEntity.ok(
            mapper.bookDto(bookService.updateBook(mapper.requestBook(updateBook), id))
        );
    }

    @PatchMapping("/addAuthorToBook/{bookId}")
    public ResponseEntity<BookResponse> addAuthorToBook(
            @PathVariable int bookId,
            @Valid @RequestBody Author newAuthor) {
        return ResponseEntity.ok(
                mapper.bookDto(bookService.addAuthorToBook(bookId, newAuthor))
        );
    }

    @PatchMapping("/addCategoriesToBook/{bookId}")
    public ResponseEntity<BookResponse> addCategoriesToBook(
            @PathVariable int bookId,
            @Valid @RequestBody List<Category> newCategories) {
        return ResponseEntity.ok(
                mapper.bookDto(bookService.addCategoriesToBook(bookId, newCategories))
        );
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllByAdmin")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> books = bookService.getBooks();
        return ResponseEntity.ok(
            books.stream().map(
                mapper::bookDto
            ).toList()
        );
    }

    @GetMapping("/getAvailable")
    public ResponseEntity<List<BookResponse>> getAvailableBooks(){
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(
                books.stream().map(
                        mapper::bookDto
                ).toList()
        );
    }

    @GetMapping("/getBooksByAuthor/{firstname}/{lastName}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(
            @PathVariable String firstname,
            @PathVariable String lastName) {
//        return ResponseEntity.ok(bookService.getBooksByAuthor(firstname, lastName));
        List<Book> books = bookService.getBooksByAuthor(firstname, lastName);
        return ResponseEntity.ok(
                books.stream().map(
                        mapper::bookDto
                ).toList()
        );
    }

    @GetMapping("/getBooksByCategory/{category}")
    public ResponseEntity<List<BookResponse>> getBooksByCategory(
            @PathVariable String category) {
//        return ResponseEntity.ok(bookService.getBooksByCategory(category));
        List<Book> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(
                books.stream().map(
                        mapper::bookDto
                ).toList()
        );
    }
}
