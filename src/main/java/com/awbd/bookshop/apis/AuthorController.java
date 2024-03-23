package com.awbd.bookshop.apis;


import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.services.author.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Author newAuthor) {
        return ResponseEntity.ok(authorService.addAuthor(token, newAuthor));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Author updateAuthor) {
        return ResponseEntity.ok(authorService.updateAuthor(token, updateAuthor, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token) {
        authorService.deleteAuthor(token, id);
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
