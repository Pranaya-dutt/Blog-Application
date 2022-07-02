package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.model.User;
import com.mountblue.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);

//        Optional<Tag> optional = tagRepository.findById(id);
//        Tag tag = null;
//        if(optional.isPresent()){
//            tag = optional.get();
//        } else {
//            throw new RuntimeException("Tag not found with id : " +id);
//        }
//        return tag;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        } else {
            throw new RuntimeException("User not found with id : " +id);
        }
        return user;
    }
}
