package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.dtos.RequestUser;
import com.awbd.bookshopspringcloud.mappers.UserMapper;
import com.awbd.bookshopspringcloud.models.Authority;
import com.awbd.bookshopspringcloud.models.User;
import com.awbd.bookshopspringcloud.services.authority.IAuthorityService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("mysql")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    IAuthorityService authorityService;

    @Test
    public void add() throws Exception{
        RequestUser user = new RequestUser("ana","m@yahoo.com","pass","Ana","Lascu");
        User user1 = mapper.requestUser(user);
        user1.setEnabled(true);
        String encoder  = passwordEncoder.encode(user.getPassword());
        user1.setPassword(encoder);

        Authority authority = new Authority("ROLE_USER");
        when(authorityService.getAuthorityByName("ROLE_USER")).thenReturn(authority);
        user1.setAuthority(authority);
        when(userService.create(user1)).thenReturn(user1);

        mockMvc.perform(post("/user/signUp")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username","ana")
                        .param("email","m@yahoo.com")
                        .param("password","pass")
                        .param("firstName","Ana")
                        .param("lastName","Lascu")
                        .param("enabled", String.valueOf(true))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1))
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void create() throws Exception{
        this.mockMvc.perform(get("/user/form")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("register"));
    }
}
