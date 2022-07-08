package com.mountblue.blog.restController;

import com.mountblue.blog.model.User;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public String saveNewUser(@RequestBody User user){
        boolean isSavedSuccessfully = userService.saveNewUser(user);
        if(isSavedSuccessfully){
            return "User Created";
        } else {
            return "User Already Exist";
        }
    }
}
