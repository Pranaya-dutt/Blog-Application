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
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.Timestamp;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        post.setAuthor("Pranaya");
        post.setPublishedAt(timestamp);
        post.setCreatedAt(timestamp);
        post.setExcerpt(post.getContent().substring(0,30)+"...");
        postService.savePost(post);

        tag.setCreatedAt(timestamp);
        try {
            tagService.saveTag(tag);
        } catch (Exception e){
            return "redirect:/newpost";
        }
        return "redirect:/newpost";
    }
}
