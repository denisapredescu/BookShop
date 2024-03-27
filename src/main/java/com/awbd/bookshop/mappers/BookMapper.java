package com.awbd.bookshop.mappers;

import com.awbd.bookshop.dtos.BookResponse;
import com.awbd.bookshop.dtos.RequestBook;
import com.awbd.bookshop.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book requestBook(RequestBook newBook) {
        return new Book(
            newBook.getName(),
            newBook.getPrice(),
            newBook.getYear(),
            newBook.getVolume(),
            newBook.getSeries_name(),
            false
        );
    }

    public BookResponse bookDto(Book book) {
        return new BookResponse(
            book.getId(),
            book.getName(),
            book.getPrice(),
            book.getYear(),
            book.getVolume(),
            book.getSeries_name(),
            book.getAuthor().getFirstName() + book.getAuthor().getLastName(),
            book.getBookCategories()
        );
    }


}
