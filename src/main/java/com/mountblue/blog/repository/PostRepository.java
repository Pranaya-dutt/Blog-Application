package com.mountblue.blog.repository;

import com.mountblue.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "select * from posts where is_published=true and (title like %?1% or content like %?1% or author like %?1%)", nativeQuery = true)
    List<Post> findAllBySearch(String keyword);

    @Query(value = "select * from posts where is_published=true and (title like %?1% or content like %?1% or author like %?1%)", nativeQuery = true)
    Page<Post> findPaginatedBySearch(Pageable pageable, String keyword);

    @Query(value = "select * from posts where is_published=true", nativeQuery = true)
    Page<Post> findPaginatedAll(Pageable pageable);

    @Query(value = "select * from posts where is_published=true", nativeQuery = true)
    List<Post> findAll();
}
