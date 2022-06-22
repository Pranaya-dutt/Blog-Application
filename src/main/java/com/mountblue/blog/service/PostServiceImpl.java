package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        Date date = new Date(System.currentTimeMillis());
        post.setAuthor("Pranaya");
        post.setPublishedAt(date);
        post.setCreatedAt(date);
        post.setExcerpt(post.getContent().substring(0,150)+"...");
        this.postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;
        if(optional.isPresent()){
            post = optional.get();
        } else {
            throw new RuntimeException("Post not found with id : " +id);
        }
        return post;
    }
}
