package com.awbd.bookshopspringcloud.services.book;

import com.awbd.bookshopspringcloud.models.Book;

import java.util.List;

public interface IBookService {
    Book addBook(Book newBook);

    Book updateBook(Book bookToUpdate, Integer id);

    void deleteBook(Integer id);

    List<Book> getBooks();

    List<Book> getAvailableBooks(Integer pageNo, Integer pageSize);

//    List<Book> getBooksByAuthor(String firstName, String lastName);
    List<Book> getBooksByAuthor(String authorName);

    List<Book> getBooksByCategory(String category);

    Book getBookById(Integer bookId);
    int numberAvailableBooks();
    List<String> getAllCategories();
    List<Book> getAllAvailableBooks();
}
