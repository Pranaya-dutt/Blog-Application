package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;

import java.util.List;

public interface PostService {
    void savePost(Post post, Tag tag);
    List<Post> getAllPosts();
    Post getPostById(int id);
}
