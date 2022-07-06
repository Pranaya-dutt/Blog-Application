package com.mountblue.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
//    @GetMapping("/home")
//    public String home(){
//        return "This is home page.";
//    }

    @GetMapping("/signin")
    public String signin(){
        return "loginpage";
    }
}
