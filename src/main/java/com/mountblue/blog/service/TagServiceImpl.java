package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag, Post post){
        Date date = new Date(System.currentTimeMillis());
        tag.setCreatedAt(date);
        post.addTag(tag);
        try{
            this.tagRepository.save(tag);
        } catch (Exception e){
            System.out.println("Tag already exist.");
        }
    }

    @Override
    public Tag getTagById(int id) {
        Optional<Tag> optional = tagRepository.findById(id);
        Tag tag = null;
        if(optional.isPresent()){
            tag = optional.get();
        } else {
            throw new RuntimeException("Tag not found with id : " +id);
        }
        return tag;
    }
}
