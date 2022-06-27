package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;

import java.util.List;

public interface PostService {
    void saveNewPost(Post post, Tag tag);
    void publishNewPost(Post post, Tag tag);
    List<Post> getAllPosts(String keyword);
    Post getPostById(int id);
    void saveUpdatePost(Post post, Tag tag);
    void publishUpdatePost(Post post, Tag tag);
}
