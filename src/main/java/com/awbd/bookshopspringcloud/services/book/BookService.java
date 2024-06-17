package com.awbd.bookshopspringcloud.services.book;

import com.awbd.bookshopspringcloud.exceptions.exceptions.DeletedBookException;
import com.awbd.bookshopspringcloud.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshopspringcloud.models.Book;
import com.awbd.bookshopspringcloud.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Transactional
    @Override
    public Book updateBook(Book bookToUpdate, Integer id) {
        Book book = getBookById(id);

        if (book.getIs_deleted())
            throw new DeletedBookException("Cannot update a deleted book");

        book.setName(bookToUpdate.getName());
        book.setSeries_name(bookToUpdate.getSeries_name());
        book.setPrice(bookToUpdate.getPrice());
        book.setVolume(bookToUpdate.getVolume());
        book.setYear(bookToUpdate.getYear());
        book.setAuthor_name(bookToUpdate.getAuthor_name());
        book.setCategories(bookToUpdate.getCategories());

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Integer id) {
        Book book = getBookById(id);
        book.setIs_deleted(true);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAvailableBooks(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
        Page<Book> pagedResult =  bookRepository.getAvailableBooks(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Book>();
        }
    }
    @Override
    public List<Book> getAllAvailableBooks() {

        return bookRepository.getAllAvailableBooks();
    }


//    @Override
//    public List<Book> getBooksByAuthor(String firstName, String lastName) {
//        return bookRepository.getBooksByAuthor(firstName, lastName);
//    }
    @Override
    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.getBooksByAuthor(authorName);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.getBooksByCategory(category);
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new NoFoundElementException("Book with this id not found")
        );
    }

    @Override
    public int numberAvailableBooks() {
        return bookRepository.getAllAvailableBooks().size();
    }

    @Override
    public List<String> getAllCategories(){
        return bookRepository.getCategories();
    }
}