package com.awbd.bookshop.services.author;

import com.awbd.bookshop.models.Author;

import java.util.List;

public interface IAuthorService {
    Author addAuthor(String token, Author newAuthor);
    Author save(Author newAuthor);
    Author updateAuthor(String token, Author newAuthor, int id);
    void deleteAuthor(String token, int id);
    List<Author> getAuthors();
    Author getAuthor(String firstName, String lastName);
}