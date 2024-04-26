package com.awbd.bookshop.apis;


import com.awbd.bookshop.mappers.AuthorMapper;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.services.author.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @RequestMapping("/add")
    public ModelAndView addAuthor(Model model){
        model.addAttribute("author",new Author());
        return new ModelAndView("authorAddForm");
    }

    @RequestMapping("/update/{id}")
    public ModelAndView updateAuthor(
            @PathVariable int id,
            @Valid Model model){
        model.addAttribute("author",authorService.getAuthorById(id));
        return new ModelAndView("authorForm");
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteAuthor(
            @PathVariable int id
    ){
        authorService.deleteAuthor(id);
        return new ModelAndView("redirect:/author");
    }

    @RequestMapping("")
    public ModelAndView getAuthors(Model model){
        List<Author> authors = authorService.getAuthors();
        model.addAttribute("authors",authors);
        return new ModelAndView ("authorList");
    }

}
