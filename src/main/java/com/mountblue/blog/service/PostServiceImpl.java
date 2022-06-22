package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setAuthor("Pranaya");
        post.setPublishedAt(timestamp);
        post.setCreatedAt(timestamp);
        post.setExcerpt(post.getContent().substring(0,30)+"...");
        this.postRepository.save(post);
    }
}
