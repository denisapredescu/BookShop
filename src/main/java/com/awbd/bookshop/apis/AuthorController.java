package com.awbd.bookshop.apis;


import com.awbd.bookshop.dtos.RequestAuthor;
import com.awbd.bookshop.mappers.AuthorMapper;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.services.author.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    final IAuthorService authorService;
    final AuthorMapper mapper;

    public AuthorController(IAuthorService authorService, AuthorMapper mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(
            @Valid @RequestBody RequestAuthor newAuthor) {
        return ResponseEntity.ok(authorService.addAuthor(mapper.requestAuthor(newAuthor)));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable int id,
            @Valid @RequestBody RequestAuthor updateAuthor) {
        return ResponseEntity.ok(authorService.updateAuthor(mapper.requestAuthor(updateAuthor), id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable int id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAuthors")
    public ResponseEntity<List<Author>> getAuthors(){
        return ResponseEntity.ok(authorService.getAuthors());
    }

    @GetMapping("/getAuthor/{firstName}/{lastName}")
    public ResponseEntity<Author> getAuthor(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        return ResponseEntity.ok(authorService.getAuthor(firstName, lastName));
    }
}
