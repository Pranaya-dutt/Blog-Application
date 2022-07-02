package com.mountblue.blog.service;

import com.mountblue.blog.model.User;

public interface UserService {

    User getUserByName(String name);

    User getUserById(int id);
}
