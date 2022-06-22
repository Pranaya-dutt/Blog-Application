package com.mountblue.blog.service;

import com.mountblue.blog.model.Tag;
import com.mountblue.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag){
        Date date = new Date(System.currentTimeMillis());
        tag.setCreatedAt(date);
        try{
            this.tagRepository.save(tag);
        } catch (Exception e){
            System.out.println("Tag already exist.");
        }
    }
}
