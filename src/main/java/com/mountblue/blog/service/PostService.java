package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;

import java.util.List;

public interface PostService {
    void savePost(Post post);
    List<Post> getAllPosts();
    Post getPostById(int id);

}
