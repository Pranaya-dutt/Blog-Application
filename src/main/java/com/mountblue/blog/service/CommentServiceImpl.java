package com.mountblue.blog.service;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        Date date = new Date(System.currentTimeMillis());
        comment.setCreatedAt(date);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentListByPostId(int id) {
        List<Comment> comments = commentRepository.findAllByPostId(id);
        return comments;
    }

    @Override
    public Comment getCommentById(int id) {
        Optional<Comment> optional = commentRepository.findById(id);
        Comment comment =null;
        if(optional.isPresent()){
            comment = optional.get();
        } else {
            throw new RuntimeException("Comment not found with id : " +id);
        }
        return comment;
    }

    @Override
    public void updateComment(Comment comment) {
        Date date = new Date(System.currentTimeMillis());
        comment.setUpdatedAt(date);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
