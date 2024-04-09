package com.awbd.bookshop.apis;

import com.awbd.bookshop.config.SecurityConfiguration;
import com.awbd.bookshop.dtos.RequestAuthor;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest
@AutoConfigureMockMvc
@SpringBootTest
public class AuthorControllerTest {

    private static final Author INPUT_AUTHOR = new Author(
            null,
            "firstName",
            "lastName",
            "nationality"
    );
    private static final Author AUTHOR = new Author(
            INPUT_AUTHOR.getId(),
            INPUT_AUTHOR.getFirstName(),
            INPUT_AUTHOR.getLastName(),
            INPUT_AUTHOR.getNationality()
    );
    private static final Integer AUTHOR_ID = 0;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;
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

}
