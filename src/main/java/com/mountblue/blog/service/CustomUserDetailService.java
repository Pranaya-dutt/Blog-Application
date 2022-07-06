package com.mountblue.blog.service;

import com.mountblue.blog.model.CustomUserDetail;
import com.mountblue.blog.model.User;
import com.mountblue.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getUserByName(username);
        if(user==null){
            throw new UsernameNotFoundException("No User");
        }
        return new CustomUserDetail(user);
    }
}
