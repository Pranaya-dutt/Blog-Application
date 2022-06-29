package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;

import java.util.ArrayList;
import java.util.List;

public interface TagService {
    void saveTag(Tag tag, Post post);
    Tag getTagById(int id);
    List<Tag> getAllTags();
}
