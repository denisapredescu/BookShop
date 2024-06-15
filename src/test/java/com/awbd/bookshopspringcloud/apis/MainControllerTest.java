package com.awbd.bookshopspringcloud.apis;

import com.awbd.bookshopspringcloud.services.author.IAuthorService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("mysql")
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IUserService userService;

    @Test
    public void getHome() throws Exception {
        this.mockMvc.perform(get("")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    public void showLogInForm() throws Exception {
        this.mockMvc.perform(get("/login")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void accessDeniedPage() throws Exception {
        this.mockMvc.perform(get("/access_denied")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"));
    }
}
