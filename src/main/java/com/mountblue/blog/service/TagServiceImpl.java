package com.mountblue.blog.service;

import com.mountblue.blog.model.Tag;
import com.mountblue.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tag.setCreatedAt(timestamp);
        try{
            this.tagRepository.save(tag);
        } catch (Exception e){
            System.out.println("Tag already exist.");
        }
    }
}
