package com.mountblue.blog.repository;

import com.mountblue.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select post from Post post where post.title like %?1%"
            + " or post.excerpt like %?1%"
            + " or post.content like %?1%"
            + " or post.author like %?1%")
    public List<Post> findAll(String keyword);
}
