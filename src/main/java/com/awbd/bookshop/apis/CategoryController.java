package com.awbd.bookshop.apis;


import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.services.category.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Category newCategory){
        return ResponseEntity.ok(categoryService.addCategory(token, newCategory));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token,
            @Valid @RequestBody Category updateCategory){
        return ResponseEntity.ok(categoryService.updateCategory(token, updateCategory, id));
    }

    @DeleteMapping("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> deleteCategory(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token){
        categoryService.deleteCategory(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getCategories")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
