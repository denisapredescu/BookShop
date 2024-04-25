package com.awbd.bookshop.apis;


import com.awbd.bookshop.dtos.RequestAuthor;
import com.awbd.bookshop.mappers.AuthorMapper;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.services.author.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

//    @PostMapping("/add")
//    public ResponseEntity<Author> addAuthor(
//            @Valid @RequestBody RequestAuthor newAuthor) {
//        return ResponseEntity.ok(authorService.addAuthor(mapper.requestAuthor(newAuthor)));
//    }
    @PostMapping("")
    public ModelAndView save(
            @Valid @ModelAttribute("author") Author author,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("author",author);
            return new ModelAndView("authorAddForm");
        }
        authorService.addAuthor(author);
        return new ModelAndView("redirect:/author");
    }

    @PostMapping("/update")
    public ModelAndView saveAuthorUpdate(
            @Valid @ModelAttribute("author") Author author,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("author",author);
            return new ModelAndView("authorForm");
        }
        authorService.updateAuthor(author,author.getId());
        return new ModelAndView("redirect:/author");
    }
//    @RequestMapping("/add")
//    public ModelAndView addAuthor(
//            @Valid Model model,
//            RequestAuthor newAuthor){
//        model.addAttribute("author",mapper.requestAuthor(newAuthor));
//        return new ModelAndView("authorAddForm");
//    }
@RequestMapping("/add")
public ModelAndView addAuthor(Model model){
    model.addAttribute("author",new Author());//mapper.requestAuthor(new RequestAuthor()));
    return new ModelAndView("authorAddForm");
}
//    @PatchMapping("/update/{id}")
//    public ResponseEntity<Author> updateAuthor(
//            @PathVariable int id,
//            @Valid @RequestBody RequestAuthor updateAuthor) {
//        return ResponseEntity.ok(authorService.updateAuthor(mapper.requestAuthor(updateAuthor), id));
//    }
//@PatchMapping("/update/{id}")
//public ModelAndView uAuthor(
//        @PathVariable int id,
//        @Valid @ModelAttribute RequestAuthor author) {
//    System.out.println(id+"");
//    authorService.updateAuthor(mapper.requestAuthor(author), id);
//    return new ModelAndView("redirect:/author");
//}

    @RequestMapping("/update/{id}") //cand merg pe ruta asta doar se afiseaza categoryForm
    public ModelAndView updateAuthor(
            @PathVariable int id,
            @Valid Model model){

        model.addAttribute("author",authorService.getAuthorById(id));


        return new ModelAndView("authorForm");
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteAuthor(
//            @PathVariable int id) {
//        authorService.deleteAuthor(id);
//        return ResponseEntity.noContent().build();
//    }
    @RequestMapping("/delete/{id}")
    public ModelAndView deleteAuthor(
            @PathVariable int id
    ){
        authorService.deleteAuthor(id);
        return new ModelAndView("redirect:/author");
    }

//    @GetMapping("/getAuthors")
//    public ResponseEntity<List<Author>> getAuthors(){
//        return ResponseEntity.ok(authorService.getAuthors());
//    }
@RequestMapping("")
public ModelAndView getAuthors(Model model){
    List<Author> authors = authorService.getAuthors();
    model.addAttribute("authors",authors);
    return new ModelAndView ("authorList");
}

//    @GetMapping("/getAuthor/{firstName}/{lastName}")//nu l-am folosit
//    public ResponseEntity<Author> getAuthor(
//            @PathVariable String firstName,
//            @PathVariable String lastName) {
//        return ResponseEntity.ok(authorService.getAuthor(firstName, lastName));
//    }
}
