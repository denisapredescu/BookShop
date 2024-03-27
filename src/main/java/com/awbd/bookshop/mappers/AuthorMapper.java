package com.awbd.bookshop.mappers;

import com.awbd.bookshop.dtos.RequestAuthor;
import com.awbd.bookshop.dtos.RequestUser;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author requestAuthor(RequestAuthor author) {
        return new Author(
            author.getFirstName(),
            author.getLastName(),
            author.getNationality()
        );
    }
}
