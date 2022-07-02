package com.mountblue.blog.controller;

import com.mountblue.blog.model.User;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user/name/{name}")
    public String getUserByName(@PathVariable(name = "name") String name){
        User user = userService.getUserByName(name);
        System.out.println(user.getEmail());
        return null;
    }

    @GetMapping("/user/id/{id}")
    public String getUserById(@PathVariable(name = "id") int id){
        User user = userService.getUserById(id);
        System.out.println(user);
        System.out.println(user.getName());
        return null;
    }

//    @GetMapping("/user/addUser/{name}/{email}/{password}")
//    public String saveUser(@PathVariable(name = "name") String name, @PathVariable(name = "email") String email, @PathVariable(name = "password") String password){
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//    }
}
