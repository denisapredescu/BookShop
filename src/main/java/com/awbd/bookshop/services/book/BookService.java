package com.awbd.bookshop.services.book;

import com.awbd.bookshop.exceptions.exceptions.DeletedBookException;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.repositories.BookRepository;
import com.awbd.bookshop.services.author.IAuthorService;
import com.awbd.bookshop.services.category.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final ICategoryService categoryService;
    private final IAuthorService authorService;

    public BookService(BookRepository bookRepository, ICategoryService categoryService, IAuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Transactional
    @Override
    public Book addBook(String token, Book newBook) {
//        JwtUtil.verifyAdmin(token);
        return bookRepository.save(newBook);
    }

    @Transactional
    @Override
    public Book addAuthorToBook(String token, Integer bookId, Author newAuthor) {
//        JwtUtil.verifyAdmin(token);
        Book book = getBookById(bookId);

        if (book.getIs_deleted())
            throw new DeletedBookException("Cannot add author to a deleted book");

        Author author = authorService.save(newAuthor);
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book addCategoriesToBook(String token, Integer bookId, List<Category> newCategories) {
//        JwtUtil.verifyAdmin(token);
        Book book = getBookById(bookId);

        if (book.getIs_deleted())
            throw new DeletedBookException("Cannot add categories to a deleted book");

        Set<Category> categories = new HashSet<>(book.getBookCategories());
        for (Category category: newCategories) {
            Category addedCategory = categoryService.save(category);
            categories.add(addedCategory);
        }

        book.setBookCategories(new ArrayList<>(categories));

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(String token, Book bookToUpdate, Integer id) {
//        JwtUtil.verifyAdmin(token);
        Book book = getBookById(id);

        if (book.getIs_deleted())
            throw new DeletedBookException("Cannot update a deleted book");

        book.setName(bookToUpdate.getName());
        book.setSeries_name(bookToUpdate.getSeries_name());
        book.setPrice(bookToUpdate.getPrice());
        book.setVolume(bookToUpdate.getVolume());
        book.setYear(bookToUpdate.getYear());

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(String token, Integer id) {
//        JwtUtil.verifyAdmin(token);

        Book book = getBookById(id);
        book.setIs_deleted(true);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public List<Book> getBooks(String token) {
//        JwtUtil.verifyAdmin(token);
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String firstName, String lastName) {
        return bookRepository.getBooksByAuthor(firstName, lastName);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.getBooksByCategory(category);
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new NoSuchElementException("Book with this id not found")
        );
    }
}