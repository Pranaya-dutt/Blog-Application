package com.mountblue.blog.service;

import com.mountblue.blog.model.Tag;
import com.mountblue.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImplementation implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag){
        this.tagRepository.save(tag);
    }
}
