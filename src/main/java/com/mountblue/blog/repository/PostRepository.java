package com.mountblue.blog.repository;

import com.mountblue.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "select * from posts where is_published=true and (posts.title like %?1% or posts.content like %?1% or posts.author like %?1% or posts.id in (select post_id from post_tag inner join tags on tags.id = post_tag.tag_id where tags.name like %?1%))", nativeQuery = true)
    List<Post> findAllBySearch(String keyword);

    @Query(value = "select * from posts where is_published=true and (posts.title like %?1% or posts.content like %?1% or posts.author like %?1% or posts.id in (select post_id from post_tag inner join tags on tags.id = post_tag.tag_id where tags.name like %?1%))", nativeQuery = true)
    Page<Post> findPaginatedBySearch(Pageable pageable, String keyword);

    @Query(value = "select * from posts where is_published=true", nativeQuery = true)
    Page<Post> findPaginatedAll(Pageable pageable);

    @Query(value = "select * from posts where is_published=true", nativeQuery = true)
    List<Post> findAll();

    @Query(value = "select * from posts where is_published=true and id IN"
            + " (SELECT posts.id FROM posts"
            + " INNER JOIN post_tag ON posts.id=post_tag.post_id INNER JOIN tags ON tags.id = post_tag.tag_id"
            + " WHERE  (lower(title) like %:search% or lower(content) like %:search% or lower(author) like %:search%)"
            + " and tags.id In :tagIds)", nativeQuery = true)
    Page<Post> findFilteredPostsWithRestApi(Pageable pageable, @Param("search") String search, @Param("tagIds") List<Integer> tagIds);
}
