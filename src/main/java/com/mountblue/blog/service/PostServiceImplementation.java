package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }
}
