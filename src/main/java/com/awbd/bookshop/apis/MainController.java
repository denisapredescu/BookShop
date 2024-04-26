package com.awbd.bookshop.apis;

import com.awbd.bookshop.services.user.IUserService;
import com.awbd.bookshop.services.user.UserService;
import jakarta.jws.soap.SOAPBinding;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class MainController {
    IUserService userService;
    public MainController(IUserService userService){
        this.userService=userService;
    }

    @RequestMapping({"","/","/home"})
    public ModelAndView getHome(){
        return new ModelAndView("main");
    }

    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }

}