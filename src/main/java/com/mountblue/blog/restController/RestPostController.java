package com.mountblue.blog.restController;

import com.mountblue.blog.exceptionHandling.PostNotFoundException;
import com.mountblue.blog.model.*;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RestPostController {
    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts")
    public Post publishNewPost(@RequestBody RequestModel requestModel, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = requestModel.getPost();
        Tag tag = requestModel.getTag();
        post.setAuthor(customUserDetail.getUsername());
        postService.publishNewPost(post, tag);
        return post;
    }

    @PutMapping("/posts/{id}")
    public String publishUpdatePost(@PathVariable(value = "id") int id,@RequestBody RequestModel requestModel, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post oldPost = postService.getPostById(id);
        if(!oldPost.getAuthor().equals(customUserDetail.getUsername()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "You are not allowed to update this post";
        }
        Post post = requestModel.getPost();
        post.setId(id);
        Tag tag = requestModel.getTag();
        postService.publishUpdatePost(post, tag);
        return "Post Updated";
    }

    @GetMapping("/posts/{id}")
    public Post showPost(@PathVariable (value = "id") int id){
        try{
            Post post = postService.getPostById(id);
            return post;
        }catch (NullPointerException nullPointerException){
            throw new PostNotFoundException("Post not found with Id: " + id);
        }
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable(value = "id") int id, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(id);
        System.out.println(customUserDetail.getUsername());
        if(!post.getAuthor().equals(customUserDetail.getUsername()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "You are not allowed to delete this post";
        }
        postService.deletePostById(id);
        return "Post Deleted";
    }

    @PostMapping("/saveNewPost")
    public Post saveNewPost(@RequestBody RequestModel requestModel, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = requestModel.getPost();
        Tag tag = requestModel.getTag();
        post.setAuthor(customUserDetail.getUsername());
        postService.saveNewPost(post, tag);
        return post;
    }

    @PutMapping("/saveUpdatePost/{id}")
    public String saveUpdatePost(@PathVariable(value = "id") int id,@RequestBody RequestModel requestModel, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post oldPost = postService.getPostById(id);
        if(!oldPost.getAuthor().equals(customUserDetail.getUsername()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "You are not allowed to update this post";
        }
        Post post = requestModel.getPost();
        post.setId(id);
        Tag tag = requestModel.getTag();
        postService.saveUpdatePost(post, tag);
        return "Post Updated";
    }
}
