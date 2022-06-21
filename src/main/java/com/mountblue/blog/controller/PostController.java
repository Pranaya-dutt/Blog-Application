package com.mountblue.blog.controller;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/newpost")
    public String addNewPost(Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "newpost";
    }

    @PostMapping("/saveNewPost")
    public String saveNewPost(@ModelAttribute("post") Post post){
        postService.savePost(post);
        return "redirect:/newpost";
    }

}
