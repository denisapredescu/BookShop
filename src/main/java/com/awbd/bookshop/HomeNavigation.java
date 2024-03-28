package com.awbd.bookshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeNavigation {
//    @GetMapping("/")
//    public String home(){
//        return ("<h2>Hi</h2>");
//    }
//
//    @GetMapping("/user")
//    public String user(){
//        return ("<h2>Hi user</h2>");
//    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h2>Hi admin</h2>");
    }
}
