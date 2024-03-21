package com.awbd.bookshop.services.book;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;

import java.util.List;

public interface IBookService {
    Book addBook(String token, Book newBook);
    Book addAuthorToBook(String token, Integer bookId, Author author);
    Book addCategoriesToBook(String token, Integer bookId, List<Category> categories);
    Book updateBook(String token,  Book bookToUpdate, Integer id);
    void deleteBook(String token, Integer id);
    List<Book> getBooks(String token);
    List<Book> getAvailableBooks();
    List<Book> getBooksByAuthor(String firstName, String lastName);
    List<Book> getBooksByCategory(String category);
    Book getBookById(Integer bookId);
}
