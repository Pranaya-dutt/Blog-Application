package com.mountblue.blog.restController;

import com.mountblue.blog.exceptionHandling.AccessDeniedException;
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
    public Post saveUpdatedComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "commentId") int commentId, @RequestBody String text, @AuthenticationPrincipal CustomUserDetail customUserDetail) throws Exception {
        Post post = postService.getPostById(postId);
        if(!customUserDetail.getUsername().equals(post.getAuthor()) && !customUserDetail.getUsername().equals("Pranaya")){
            throw new AccessDeniedException("You are not allowed to update this comment");
        }
        Comment comment = commentService.getCommentById(commentId);
        if(comment.getPostId()!=postId){
            throw new Exception("Comment does't belong to this post.");
        }
        comment.setText(text);
        commentService.updateComment(comment);
        return post;
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public Post deleteComment(@PathVariable(value = "postId") int postId,@PathVariable(value = "commentId") int commentId, @AuthenticationPrincipal CustomUserDetail customUserDetail) throws Exception {
        Post post = postService.getPostById(postId);
        if(!customUserDetail.getUsername().equals(post.getAuthor()) && !customUserDetail.getUsername().equals("Pranaya")){
            throw new AccessDeniedException("You are not allowed to delete this comment");
//            return "You are not allowed to delete this comment";
        }
        Comment comment= commentService.getCommentById(commentId);
        if(comment.getPostId()!=postId){
            throw new Exception("Comment doesn't belong to this post.");
        }
        commentService.deleteComment(comment);
//        return "Comment Deleted";
        return post;
    }
}
