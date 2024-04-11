package com.awbd.bookshop.apis;

import com.awbd.bookshop.config.SecurityConfiguration;
import com.awbd.bookshop.dtos.RequestAuthor;
import com.awbd.bookshop.mappers.AuthorMapper;
import com.awbd.bookshop.models.Author;
import com.awbd.bookshop.services.author.AuthorService;
import com.awbd.bookshop.services.author.IAuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Profile("mysql")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAuthorService authorService;
    @MockBean
    private Model model;
    @MockBean
    private AuthorMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    //@WithMockUser(username = "miruna",roles = {"USER"})
    public void save() throws Exception{
        Author author = new Author("Lara","Simon","Romanian");
        System.out.println(author);
        when(authorService.addAuthor(author)).thenReturn(new Author(1,"Lara","Simon","Romanian"));
        mockMvc.perform(post("/author")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author))
                )
               .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));

    }

    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void saveAuthorUpdate() throws Exception{
        //  authorService.updateAuthor(author,author.getId());
        int id = 1;
        Author request = new Author("Lara","Simon","Romanian");
        request.setId(id);
        Author author = new Author(id,"Lara","Simoni","Romanian");
        when(authorService.updateAuthor(request, id)).thenReturn(author);

        mockMvc.perform(post("/author/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("author",request)
                     //   .content(objectMapper.writeValueAsString(newAuthor))

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));

    }
//        @RequestMapping("/add")
//        public ModelAndView addAuthor(
//                @Valid Model model,
//                @Valid RequestAuthor newAuthor){
//            model.addAttribute("author",mapper.requestAuthor(newAuthor));
//            return new ModelAndView("authorAddForm");
//        }
    @Test
    @WithMockUser(username = "miruna",password = "pass",roles = {"USER"})
    public void addAuthor() throws Exception {

        RequestAuthor rAuthor = new RequestAuthor();
        Author author = new Author();
      //  System.out.println("Author after mapping: " + author);
        this.mockMvc.perform(get("/author/add")
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"));
               // .andExpect(model().attributeExists("author"))
               // .andExpect(view().name("authorAddForm"));

    }

//        this.mockMvc.perform(MockMvcRequestBuilders.post("/author/add")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(rAuthor))
//                        .flashAttrs(model)
//                )
//
//                .andExpect(status().isOk());
//                //.andExpect(view().name("authorAddForm"));
//    }

}
