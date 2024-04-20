package com.awbd.bookshop.services.author;

import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(Author newAuthor) {
        return save(newAuthor);
    }

    @Transactional
    @Override
    public Author save(Author newAuthor) {
        if (newAuthor == null)
            return null;

        Author alreadyIn = getAuthor(newAuthor.getFirstName(), newAuthor.getLastName());

        if (alreadyIn == null)
            return authorRepository.save(newAuthor);

        return  alreadyIn;
    }

    @Transactional
    @Override
    public Author updateAuthor(Author newAuthor, int id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new NoFoundElementException("Author with this id not found"));

        author.setFirstName(newAuthor.getFirstName());
        author.setLastName(newAuthor.getLastName());
        author.setNationality(newAuthor.getNationality());
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.getAuthors();
    }

    @Override
    public Author getAuthor(String firstName, String lastName) {
        return authorRepository.getAuthor(firstName, lastName).orElse(null);
    }

    @Override
    public Author getAuthorById(int id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new NoFoundElementException("Author with this id not found"));
        return author;
    }
}