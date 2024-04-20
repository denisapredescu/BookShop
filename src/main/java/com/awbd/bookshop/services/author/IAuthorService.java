package com.awbd.bookshop.services.author;

import com.awbd.bookshop.models.Author;

import java.util.List;

public interface IAuthorService {
    Author addAuthor(Author newAuthor);

    Author save(Author newAuthor);

    Author updateAuthor(Author newAuthor, int id);

    void deleteAuthor(int id);

    List<Author> getAuthors();

    Author getAuthor(String firstName, String lastName);

    Author getAuthorById(int id);
}