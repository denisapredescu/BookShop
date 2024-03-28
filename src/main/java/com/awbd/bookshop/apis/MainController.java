package com.awbd.bookshop.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @RequestMapping({"","/","/home"})
    public ModelAndView getHome(){
        return new ModelAndView("main");
    }

//    @RequestMapping({"/category"})
//    public ModelAndView getCategoties(){
//
//        return new ModelAndView("categoryList");
//    }
    @RequestMapping({"/category/form"})
    public ModelAndView getCategotiesForm(){

        return new ModelAndView("categoryForm");
    }
}