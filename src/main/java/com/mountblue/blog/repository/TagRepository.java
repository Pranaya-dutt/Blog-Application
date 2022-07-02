package com.mountblue.blog.repository;

import com.mountblue.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag getTagByName(String name);
}
