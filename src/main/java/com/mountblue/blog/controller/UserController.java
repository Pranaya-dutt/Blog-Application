package com.mountblue.blog.controller;

import com.mountblue.blog.model.User;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignUpPage(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "signuppage";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") User user){
        boolean isSavedSuccessfully = userService.saveNewUser(user);
        if(isSavedSuccessfully){
            return "redirect:/signin";
        } else {
            return "redirect:/signup?error";
        }
    }

//    @GetMapping("/accessDenied")
//    public String accessDenied(){
//        return "accessdenied";
//    }
}
