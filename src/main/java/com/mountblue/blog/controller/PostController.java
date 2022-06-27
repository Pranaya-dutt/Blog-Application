package com.mountblue.blog.controller;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword){
        model.addAttribute("postList", postService.getAllPosts(keyword));
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
    public String showPost(@PathVariable (value = "id") int id, Model model, Model commentModel){
        Post post = postService.getPostById(id);
        Comment comment = new Comment();
        List<Comment> commentList = commentService.getCommentListByPostId(id);
        model.addAttribute("post", post);
        commentModel.addAttribute("comment", comment);
        commentModel.addAttribute("commentList", commentList);
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
    public String draftPage(Model model, @Param("keyword") String keyword){
        model.addAttribute("postList", postService.getAllPosts(keyword));
        return "draftspage";
    }
}
