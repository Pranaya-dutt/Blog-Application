package com.mountblue.blog.controller;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.CustomUserDetail;
import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/newpost")
    public String addNewPost(Model model,@AuthenticationPrincipal CustomUserDetail customUserDetail){
        Tag tag = new Tag();
        Post post = new Post();
        model.addAttribute("customUserDetail", customUserDetail);
        model.addAttribute("post", post);
        model.addAttribute("tag", tag);
        return "newpost";
    }

    @PostMapping("/saveNewPost")
    public String saveNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag,  @AuthenticationPrincipal CustomUserDetail customUserDetail){
        post.setAuthor(customUserDetail.getUsername());
        postService.saveNewPost(post, tag);
        return "redirect:/draftPage";
    }

    @PostMapping("/publishNewPost")
    public String publishNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        post.setAuthor(customUserDetail.getUsername());
        postService.publishNewPost(post, tag);
        return "redirect:/";
    }

    @PostMapping("/saveUpdatePost")
    public String saveUpdatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.saveUpdatePost(post, tag);
        return "redirect:/draftPage";
    }
    @PostMapping("/publishUpdatePost")
    public String publishUpdatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.publishUpdatePost(post, tag);
        return "redirect:/";
    }

    @GetMapping("/showPost/{id}")
    public String showPost(@PathVariable (value = "id") int id, Model model, Model commentModel, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(id);
        Comment comment = new Comment();
        List<Comment> commentList = commentService.getCommentListByPostId(id);
        if(customUserDetail != null){
            model.addAttribute("username", customUserDetail.getUsername());
        }
        model.addAttribute("post", post);
        commentModel.addAttribute("comment", comment);
        commentModel.addAttribute("commentList", commentList);
        return "blogpage";
    }


    @GetMapping("/updatePost/{id}")
    public String updatePost(@PathVariable (value = "id") int id, Model model,@AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(id);
        if(!post.getAuthor().equals(customUserDetail.getUsername()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "accessdenied";
        }
        model.addAttribute("post", post);
        Tag postTag = new Tag();
        List<Tag> postTagList = post.getTags();
        Tag tag1 = postTagList.get(0);
        String allTags = tag1.getName();
        for(int i=1; i<postTagList.size();i++){
            Tag tag = postTagList.get(i);
            String name = ", " + tag.getName();
            allTags += name;
        }
        postTag.setName(allTags);
        model.addAttribute("customUserDetail", customUserDetail);
        model.addAttribute("tag",postTag);
        return "updatepost";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") int id, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(id);
        if(!post.getAuthor().equals(customUserDetail.getUsername()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "accessdenied";
        }
        postService.deletePostById(id);
        return "redirect:/";
    }
}
