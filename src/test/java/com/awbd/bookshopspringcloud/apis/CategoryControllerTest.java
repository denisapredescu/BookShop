//package com.awbd.bookshop.apis;
//
//import com.awbd.bookshop.models.Category;
//import com.awbd.bookshop.services.category.ICategoryService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.ui.Model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ActiveProfiles("mysql")
//public class CategoryControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private ICategoryService categoryService;
//    @MockBean
//    private Model model;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void save() throws Exception{
//        Category category = new Category();
//        category.setName("categ");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(category));
//
//        when(categoryService.addCategory(eq(category))).thenReturn(new Category("categ"));
//        mockMvc.perform(post("/category")
//                        .with(csrf())
//                        .param("name", "categ")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(category))
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/category"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveErr() throws Exception{
//        Category category = new Category();
//        category.setName("");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(category));
//
//        mockMvc.perform(post("/category")
//                        .with(csrf())
//                        .param("name", "")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(category))
//                )
//                .andExpect(model().attributeExists("category"))
//                .andExpect(view().name("categoryAddForm"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveUpdate() throws Exception{
//        Category category = new Category();
//        category.setName("categ");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(category));
//
//        when(categoryService.addCategory(eq(category))).thenReturn(new Category("categ"));
//        mockMvc.perform(post("/category/updated")
//                        .with(csrf())
//                        .param("name", "categ")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(category))
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/category"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveUpdateErr() throws Exception{
//        Category category = new Category();
//        category.setName("");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(category));
//
//        when(categoryService.addCategory(eq(category))).thenReturn(new Category("categ"));
//        mockMvc.perform(post("/category/updated")
//                        .with(csrf())
//                        .param("name", "")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(category))
//                )
//                .andExpect(model().attributeExists("category"))
//                .andExpect(view().name("categoryForm"));
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void updateCategory() throws Exception {
//        int id = 1;
//        Category category = new Category(1,"action");
//        when(categoryService.getCategoryById(id)).thenReturn(category);
//        mockMvc.perform(get("/category/update/{id}","1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("categoryForm"))
//                .andExpect(model().attribute("category",category));
//        verify(categoryService,times(1)).getCategoryById(id);
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void deleteCategory() throws Exception{
//        int id=1;
//        mockMvc.perform(delete("/category/delete/{id}","1")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/category"));
//        verify(categoryService).deleteCategory(id);
//    }
//
//    @Test
//    public void getCategories() throws Exception{
//        Category category1 = new Category(1,"action");
//        Category category2 = new Category(2,"romance");
//
//        List<Category> categories = new ArrayList<>();
//        categories.add(category1);
//        categories.add(category2);
//        when(categoryService.getCategories()).thenReturn(categories);
//
//        mockMvc.perform(get("/category"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("categoryList"))
//                .andExpect(model().attribute("categories",categories));
//        verify(categoryService,times(1)).getCategories();
//    }
//}
