package com.awbd.bookshop.apis;

import com.awbd.bookshop.services.author.IAuthorService;
import com.awbd.bookshop.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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

    //    @RequestMapping({"","/","/home"})
    //    public ModelAndView getHome(){
    //      //  int userId = getCurrentUserId();
    //        //model.addAttribute("userId", userId);
    //        return new ModelAndView("main");
    //    }
    @Test
    public void getHome() throws Exception {
        this.mockMvc.perform(get("")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    // @GetMapping("/login")
    //    public String showLogInForm(){ return "login"; }

    @Test
    public void showLogInForm() throws Exception {
        this.mockMvc.perform(get("/login")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    //    @GetMapping("/access_denied")
    //    public String accessDeniedPage(){ return "accessDenied"; }

    @Test
    public void accessDeniedPage() throws Exception {
        this.mockMvc.perform(get("/access_denied")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"));
    }
}
