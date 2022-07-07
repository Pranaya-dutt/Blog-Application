package com.mountblue.blog.controller;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.CustomUserDetail;
import com.mountblue.blog.model.Post;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/showPost/{id}/saveComment")
    public String saveComment(@PathVariable(value = "id") int id, @ModelAttribute("comment") Comment comment, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        comment.setPostId(id);
        if(comment.getName() == null){
            comment.setName(customUserDetail.getUsername());
            comment.setEmail(customUserDetail.getEmail());
        }
        commentService.saveComment(comment);
        return "redirect:/showPost/{id}";
    }

    @GetMapping("/showPost/{id}/updateComment/{commentId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId,@PathVariable(name = "id") int postId, Model model){
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("comment",comment);
        return "commenteditform";
    }

    @PostMapping("/showPost/{postId}/saveUpdatedComment/{id}")
    public String saveUpdatedComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "id") int id, @RequestParam("text") String text, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Comment comment = commentService.getCommentById(id);
        comment.setText(text);
        commentService.updateComment(comment);
        return "redirect:/showPost/{postId}";
    }

    @GetMapping("/showPost/{postId}/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "id") int id){
        Comment comment= commentService.getCommentById(id);
        commentService.deleteComment(comment);
        return "redirect:/showPost/{postId}";
    }
}
