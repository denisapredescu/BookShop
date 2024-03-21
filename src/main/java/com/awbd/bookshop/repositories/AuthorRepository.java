package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("SELECT a FROM Author a")
    List<Author> getAuthors();

    @Query("SELECT a FROM Author a WHERE LOWER(a.firstName) like LOWER(:firstName) AND LOWER(a.lastName) like LOWER(:lastName)")
    Optional<Author> getAuthor(String firstName, String lastName);
}