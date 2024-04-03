package com.awbd.bookshop.services.book;

import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Category;

import java.util.List;

public interface IBookService {
    Book addBook(Book newBook);
    Book addAuthorToBook(Integer bookId, Author author);
    Book addCategoriesToBook(Integer bookId, List<Category> categories);
    Book updateBook(Book bookToUpdate, Integer id);
    void deleteBook(Integer id);
    List<Book> getBooks();
//    List<Book> getAvailableBooks();

    List<Book> getAvailableBooks(Integer pageNo, Integer pageSize);

    List<Book> getBooksByAuthor(String firstName, String lastName);
    List<Book> getBooksByCategory(String category);
    Book getBookById(Integer bookId);
}
