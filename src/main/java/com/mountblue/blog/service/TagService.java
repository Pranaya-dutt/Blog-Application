package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;

public interface TagService {
    void saveTag(Tag tag, Post post);
    Tag getTagById(int id);
}
