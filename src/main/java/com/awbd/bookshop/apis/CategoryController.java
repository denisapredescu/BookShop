package com.awbd.bookshop.apis;


import com.awbd.bookshop.mappers.CategoryMapper;
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
    final ICategoryService categoryService;
    final CategoryMapper mapper;

    public CategoryController(ICategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(
            @Valid @RequestBody String newCategory){
        return ResponseEntity.ok(
                categoryService.addCategory(mapper.requestCategory(newCategory))
        );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable int id,
            @Valid @RequestBody String updateCategory){
        return ResponseEntity.ok(
                categoryService.updateCategory(
                        mapper.requestCategory(updateCategory),
                        id
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable int id,
            @RequestHeader(name = "userToken") String token){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
