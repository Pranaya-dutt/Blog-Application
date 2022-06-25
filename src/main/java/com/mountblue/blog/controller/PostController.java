package com.mountblue.blog.controller;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("postList", postService.getAllPosts());
        return "homepage";
    }

    @GetMapping("/newpost")
    public String addNewPost(Model postModel, Model tagModel){
        Tag tag = new Tag();
        Post post = new Post();
        postModel.addAttribute("post", post);
        tagModel.addAttribute("tag", tag);
        return "newpost";
    }

    @PostMapping("/saveNewPost")
    public String saveNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.savePost(post, tag);
        return "redirect:/draftspage";
    }
    @PostMapping("/publishNewPost")
    public String publishNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.publishPost(post, tag);
        return "redirect:/";
    }

    @GetMapping("/showPost/{id}")
    public String showPost(@PathVariable (value = "id") int id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "blogpage";
    }

    @GetMapping("/updatePost/{id}")
    public String updatePost(@PathVariable (value = "id") int id, Model model){
        Post post = postService.getPostById(id);
        //Tag tag = tagService.getTagById(id);
        model.addAttribute("post", post);
        //model.addAttribute("tag",tag);
        return "updatepost";
    }

    @GetMapping("/draftPage")
    public String draftPage(Model model){
        model.addAttribute("postList", postService.getAllPosts());
        return "draftspage";
    }
}
