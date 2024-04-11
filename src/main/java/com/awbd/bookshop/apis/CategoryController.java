package com.awbd.bookshop.apis;


import com.awbd.bookshop.mappers.CategoryMapper;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.services.category.ICategoryService;
import jakarta.validation.Valid;
import org.h2.engine.Mode;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    final ICategoryService categoryService;
    final CategoryMapper mapper;

    public CategoryController(ICategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

//    @PostMapping("/add")
//    public ResponseEntity<Category> addCategory(
//            @Valid @RequestBody String newCategory){
//        return ResponseEntity.ok(
//                categoryService.addCategory(mapper.requestCategory(newCategory))
//        );
//    }
@PostMapping("")
public ModelAndView save(
        @Valid @ModelAttribute Category category){
        categoryService.addCategory(category);
        return new ModelAndView("redirect:/category");
}

//    @PatchMapping("/update/{id}")
//    public ResponseEntity<Category> updateCategory(
//            @PathVariable int id,
//            @Valid @RequestBody String updateCategory){
//        return ResponseEntity.ok(
//                categoryService.updateCategory(
//                        mapper.requestCategory(updateCategory),
//                        id
//                )
//        );
//    }
@RequestMapping("/add")
public ModelAndView addCategory(
        @Valid Model model,
        @Valid String newCategory){
    model.addAttribute("category",mapper.requestCategory(newCategory));
    return new ModelAndView("categoryAddForm");
}
@RequestMapping("/update/{id}") //cand merg pe ruta asta doar se afiseaza categoryForm
public ModelAndView updateCategory(
        @PathVariable int id,
        @Valid Model model){

        model.addAttribute("category",categoryService.getCategoryById(id));


    return new ModelAndView("categoryForm");
}

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteCategory(
//            @PathVariable int id
//    ){
//        categoryService.deleteCategory(id);
//        return ResponseEntity.noContent().build();
//    }
@RequestMapping("/delete/{id}")
public ModelAndView deleteCategory(
        @PathVariable int id
){
    categoryService.deleteCategory(id);
    return new ModelAndView("redirect:/category");
}

//    @GetMapping("/getCategories")
//    public ResponseEntity<List<Category>> getCategories(){
//        return ResponseEntity.ok(categoryService.getCategories());
//    }
@RequestMapping("")
public ModelAndView getCategories(Model model){
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories",categories);
        return new ModelAndView ("categoryList");
}
}
