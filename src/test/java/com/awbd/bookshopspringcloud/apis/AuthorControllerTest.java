//package com.awbd.bookshop.apis;
//
//import com.awbd.bookshop.mappers.AuthorMapper;
//import com.awbd.bookshop.services.author.IAuthorService;
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
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ActiveProfiles("mysql")
//public class AuthorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private IAuthorService authorService;
//    @MockBean
//    private Model model;
//    @MockBean
//    private AuthorMapper mapper;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Test
//    public void save() throws Exception{
//        Author author = new Author("Lara","Simon","Romanian");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(author));
//        when(authorService.addAuthor(author)).thenReturn(new Author(1,"Lara","Simon","Romanian"));
//        mockMvc.perform(post("/author")
//                        .param("firstName","Lara")
//                        .param("lastName","Simon")
//                        .param("nationality","Romanian")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(author))
//                )
//               .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/author"));
//    }
//    @Test
//    @WithMockUser(username = "ana", password = "pass", roles = "USER")
//    public void saveForb() throws Exception {
//
//        mockMvc.perform(post("/author"))
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    public void saveErr() throws Exception{
//        Author author = new Author("","Simon","Romanian");
//        System.out.println("Request JSON: " + objectMapper.writeValueAsString(author));
//        when(authorService.addAuthor(author)).thenReturn(new Author(1,"","Simon","Romanian"));
//        mockMvc.perform(post("/author")
//                        .param("firstName","")
//                        .param("lastName","Simon")
//                        .param("nationality","Romanian")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(author))
//                )
//                .andExpect(model().attributeExists("author"))
//                .andExpect(view().name("authorAddForm"));
//    }
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveAuthorUpdate() throws Exception{
//        int id = 1;
//        Author request = new Author("Lara","Simon","Romanian");
//        request.setId(id);
//        Author author = new Author(id,"Lara","Simoni","Romanian");
//        when(authorService.updateAuthor(request, id)).thenReturn(author);
//
//        mockMvc.perform(post("/author/update")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .flashAttr("author",request)
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/author"));
//        verify(authorService,times(1)).updateAuthor(request,id);
//
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void saveAuthorUpdateErr() throws Exception{
//        int id = 1;
//        Author request = new Author("","Simon","Romanian");
//        request.setId(id);
//        Author author = new Author(id,"","Simoni","Romanian");
//        when(authorService.updateAuthor(request, id)).thenReturn(author);
//
//        mockMvc.perform(post("/author/update")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .flashAttr("author",request)
//                )
//                .andExpect(model().attributeExists("author"))
//                .andExpect(view().name("authorForm"));
//    }
//    @Test
//    @WithMockUser(username = "ana", password = "pass", roles = "USER")
//    public void saveAuthorUpdateForb() throws Exception {
//
//        mockMvc.perform(post("/author/update"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void addAuthor() throws Exception {
//        this.mockMvc.perform(get("/author/add")
//                                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("author"))
//                .andExpect(view().name("authorAddForm"));
//    }
//    @Test
//    @WithMockUser(username = "ana", password = "pass", roles = "USER")
//    public void addAuthorForb() throws Exception {
//
//        mockMvc.perform(get("/author/add"))
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void updateAuthor() throws Exception {
//        int id = 1;
//        Author author = new Author(1,"Lara","Simon","Romanian");
//        when(authorService.getAuthorById(id)).thenReturn(author);
//        mockMvc.perform(get("/author/update/{id}","1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("authorForm"))
//                .andExpect(model().attribute("author",author));
//        verify(authorService,times(1)).getAuthorById(id);
//    }
//    @Test
//    @WithMockUser(username = "ana", password = "pass", roles = "USER")
//    public void updateAuthorForb() throws Exception {
//
//        mockMvc.perform(get("/author/update/{id}","1"))
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    @WithMockUser(username = "miruna",password = "pass",roles = {"ADMIN"})
//    public void deleteAuthor() throws Exception{
//        int id=1;
//        mockMvc.perform(delete("/author/delete/{id}","1")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/author"));
//        verify(authorService).deleteAuthor(id);
//    }
//    @Test
//    @WithMockUser(username = "ana", password = "pass", roles = "USER")
//    public void deleteAuthorForb() throws Exception {
//
//        mockMvc.perform(delete("/author/delete/{id}","1"))
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    public void getAuthors() throws Exception{
//        Author author1 = new Author(1,"Lara","Simoni","Romanian");
//        Author author2 = new Author(2,"Sara","Oprea","Romanian");
//
//        List<Author> authors = new ArrayList<>();
//        authors.add(author1);
//        authors.add(author2);
//        when(authorService.getAuthors()).thenReturn(authors);
//
//        mockMvc.perform(get("/author"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("authorList"))
//                .andExpect(model().attribute("authors",authors));
//    }
//}
