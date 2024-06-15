//package com.awbd.bookshop.apis;
//
//
//import com.awbd.bookshop.mappers.CategoryMapper;
//import com.awbd.bookshop.models.Category;
//import com.awbd.bookshop.services.category.ICategoryService;
//import jakarta.validation.Valid;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//@RestController
//
//@RequestMapping("/category")
//public class CategoryController {
//    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
//    final ICategoryService categoryService;
//    final CategoryMapper mapper;
//
//    public CategoryController(ICategoryService categoryService, CategoryMapper mapper) {
//        this.categoryService = categoryService;
//        this.mapper = mapper;
//    }
//
//    @PostMapping("")
//    public ModelAndView save(
//            @Valid @ModelAttribute("category") Category category,
//            BindingResult bindingResult,
//            Model model){
//            if (bindingResult.hasErrors()){
//                model.addAttribute("category",category);
//                return new ModelAndView("categoryAddForm");
//            }
//            categoryService.addCategory(category);
//            return new ModelAndView("redirect:/category");
//    }
//
//    @RequestMapping("/add")
//    public ModelAndView addCategory(
//            Model model,
//            String newCategory){
//        model.addAttribute("category",mapper.requestCategory(newCategory));
//        return new ModelAndView("categoryAddForm");
//    }
//
//    @PostMapping("/updated")
//    public ModelAndView saveUpdate(
//            @Valid @ModelAttribute("category") Category category,
//            BindingResult bindingResult,
//            Model model)
//    {
//            if (bindingResult.hasErrors()){
//                model.addAttribute("category",category);
//                return new ModelAndView("categoryForm");
//            }
//            categoryService.addCategory(category);
//            return new ModelAndView("redirect:/category");
//    }
//    @RequestMapping("/update/{id}")
//    public ModelAndView updateCategory(
//            @PathVariable int id,
//            @Valid Model model){
//
//            model.addAttribute("category",categoryService.getCategoryById(id));
//
//
//        return new ModelAndView("categoryForm");
//    }
//
//    @RequestMapping("/delete/{id}")
//    public ModelAndView deleteCategory(
//            @PathVariable int id
//    ){
//        categoryService.deleteCategory(id);
//        return new ModelAndView("redirect:/category");
//    }
//
//    @RequestMapping("")
//    public ModelAndView getCategories(Model model){
//            List<Category> categories = categoryService.getCategories();
//            model.addAttribute("categories",categories);
//            return new ModelAndView ("categoryList");
//    }
//}
