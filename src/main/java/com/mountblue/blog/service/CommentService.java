package com.mountblue.blog.service;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment);
    List<Comment> getCommentListByPostId(int id);
    Comment getCommentById(int id);
    void updateComment(Comment comment);
    void deleteComment(Comment comment);
}
