package com.mountblue.blog.restController;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.CustomUserDetail;
import com.mountblue.blog.model.Post;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public Post saveComment(@PathVariable(value = "postId") int postId, @RequestBody Comment comment, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        comment.setPostId(postId);
        if(comment.getName() == null){
            comment.setName(customUserDetail.getUsername());
            comment.setEmail(customUserDetail.getEmail());
        }
        commentService.saveComment(comment);
        Post post = postService.getPostById(postId);
        return post;
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public String saveUpdatedComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "commentId") int commentId, @RequestBody String text, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(postId);
        if(!customUserDetail.getUsername().equals(post.getAuthor()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "You are not allowed to update this comment";
        }
        Comment comment = commentService.getCommentById(commentId);
        comment.setText(text);
        commentService.updateComment(comment);
        return "Comment Updated";
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "commentId") int commentId, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        Post post = postService.getPostById(postId);
        if(!customUserDetail.getUsername().equals(post.getAuthor()) && !customUserDetail.getUsername().equals("Pranaya")){
            return "You are not allowed to delete this comment";
        }
        Comment comment= commentService.getCommentById(commentId);
        commentService.deleteComment(comment);
        return "Comment Deleted";
    }
}
