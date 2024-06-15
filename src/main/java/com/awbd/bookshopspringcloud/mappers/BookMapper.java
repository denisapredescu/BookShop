package com.awbd.bookshopspringcloud.mappers;

import com.awbd.bookshopspringcloud.dtos.BookResponse;
import com.awbd.bookshopspringcloud.dtos.RequestBook;
import com.awbd.bookshopspringcloud.models.Book;
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
            false,
                newBook.getAuthor_name(),
                newBook.getCategories()
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
            book.getAuthor_name(),
            book.getCategories()
        );
    }


}
