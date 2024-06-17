package com.awbd.bookshopspringcloud.apis;
import com.awbd.bookshopspringcloud.dtos.RequestBook;
import com.awbd.bookshopspringcloud.mappers.BookMapper;
import com.awbd.bookshopspringcloud.models.Book;
import com.awbd.bookshopspringcloud.services.basket.IBasketService;
import com.awbd.bookshopspringcloud.services.book.IBookService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/book")
@Tag(name = "Book API", description = "Endpoints for managing books")
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


    //delete
    @Operation(summary = "Delete a book by ID. Just an user with an admin role can delete", responses = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable @Parameter(description = "Book ID", required = true) int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    //add
    @Operation(summary = "Add a new book. Just an user with an admin role can add", responses = {
            @ApiResponse(responseCode = "200", description = "Book added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New book details", required = true) RequestBook newBook){
        Book savedBook = bookService.addBook(mapper.requestBook(newBook));
        RequestBook book1 = new RequestBook(savedBook.getName(),savedBook.getPrice(),savedBook.getYear(),savedBook.getVolume(),savedBook.getSeries_name(),
                savedBook.getAuthor_name(),savedBook.getCategories());
        Link putLink = linkTo(methodOn(BookController.class).updateBook(savedBook.getId(),book1)).withRel("updateBook");
        savedBook.add(putLink);

        Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(savedBook.getId())).withRel("deleteBook");
        savedBook.add(deleteLink);

        Link getAllLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("allBooks");
        savedBook.add(getAllLink);

        Link getAvailableLink = linkTo(methodOn(BookController.class).getAvailableBooks()).withRel("getAvailableBooks");
        savedBook.add(getAvailableLink);

        return ResponseEntity.ok(savedBook);
    }

    //update
    @Operation(summary = "Update an existing book. Just an user with an admin role can update", responses = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable @Parameter(description = "Book ID", required = true) int id,
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update book details. The field that will be updated are: name, price, year, volume, series", required = true) RequestBook updateBook)
    {
        Book updatedBook = bookService.updateBook(mapper.requestBook(updateBook), id);
        RequestBook book1 = new RequestBook(updatedBook.getName(),updatedBook.getPrice(),updatedBook.getYear(),updatedBook.getVolume(),updatedBook.getSeries_name(),
                updatedBook.getAuthor_name(),updatedBook.getCategories());
        Link selfLink = linkTo(methodOn(BookController.class).updateBook(updatedBook.getId(),book1)).withRel("updateBook");
        updatedBook.add(selfLink);

        Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(updatedBook.getId())).withRel("deleteBook");
        updatedBook.add(deleteLink);

        Link getAllLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("allBooks");
        updatedBook.add(getAllLink);

        Link getAvailableLink = linkTo(methodOn(BookController.class).getAvailableBooks()).withRel("getAvailableBooks");
        updatedBook.add(getAvailableLink);

        return ResponseEntity.ok(updatedBook);
    }

    //all Books
    @Operation(summary = "Get all books.", responses = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/allBooks")
    public CollectionModel<Book> getAllBooks(){
        List<Book> books = bookService.getBooks();

        for (final Book book : books) {

            Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("deleteBook");
            book.add(deleteLink);

            RequestBook book1 = new RequestBook(book.getName(),book.getPrice(),book.getYear(),book.getVolume(),book.getSeries_name(),
                    book.getAuthor_name(),book.getCategories());

            Link postLink = linkTo(methodOn(BookController.class).addBook(book1)).withRel("saveBook");
            book.add(postLink);

            Link putLink = linkTo(methodOn(BookController.class).updateBook(book.getId(), book1)).withRel("updateBook");
            book.add(putLink);
        }

        Link link = linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel();
        CollectionModel<Book> result = CollectionModel.of(books, link);
        return result;
    }

    //available books
    @Operation(summary = "Get available books", responses = {
            @ApiResponse(responseCode = "200", description = "List of available books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAvailable")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        List<Book> books = bookService.getAllAvailableBooks();

        for (final Book book : books) {

            Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("deleteBook");
            book.add(deleteLink);

            RequestBook book1 = new RequestBook(book.getName(),book.getPrice(),book.getYear(),book.getVolume(),book.getSeries_name(),
                    book.getAuthor_name(),book.getCategories());

            Link postLink = linkTo(methodOn(BookController.class).addBook(book1)).withRel("saveBook");
            book.add(postLink);

            Link putLink = linkTo(methodOn(BookController.class).updateBook(book.getId(), book1)).withRel("updateBook");
            book.add(putLink);
        }

        return ResponseEntity.ok(books);
    }

    //books by category
    @Operation(summary = "Get books by category's name", responses = {
            @ApiResponse(responseCode = "200", description = "List of books by category retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/getBooksByCategory")
    public ResponseEntity<List<Book>> getBooksByCategory(
            @RequestParam(name = "selectedCategory") String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        for (final Book book : books) {

            Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("deleteBook");
            book.add(deleteLink);

            RequestBook book1 = new RequestBook(book.getName(),book.getPrice(),book.getYear(),book.getVolume(),book.getSeries_name(),
                    book.getAuthor_name(),book.getCategories());

            Link postLink = linkTo(methodOn(BookController.class).addBook(book1)).withRel("saveBook");
            book.add(postLink);

            Link putLink = linkTo(methodOn(BookController.class).updateBook(book.getId(), book1)).withRel("updateBook");
            book.add(putLink);
        }
        return ResponseEntity.ok(books);
    }

    //books by author
    @Operation(summary = "Get books by author's first and last name", responses = {
            @ApiResponse(responseCode = "200", description = "List of books by author retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getBooksByAuthor/{authorName}")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @PathVariable @Parameter(description = "Author's first and last name", required = true) String authorName) {
        List<Book> books = bookService.getBooksByAuthor(authorName);
        for (final Book book : books) {

            Link deleteLink = linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("deleteBook");
            book.add(deleteLink);

            RequestBook book1 = new RequestBook(book.getName(),book.getPrice(),book.getYear(),book.getVolume(),book.getSeries_name(),
                    book.getAuthor_name(),book.getCategories());

            Link postLink = linkTo(methodOn(BookController.class).addBook(book1)).withRel("saveBook");
            book.add(postLink);

            Link putLink = linkTo(methodOn(BookController.class).updateBook(book.getId(), book1)).withRel("updateBook");
            book.add(putLink);
        }
        return ResponseEntity.ok(books);
    }
}
