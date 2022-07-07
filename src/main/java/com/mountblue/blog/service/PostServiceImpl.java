package com.mountblue.blog.service;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

    public void postSaver(Post post, Tag tag){
        Date date = new Date(System.currentTimeMillis());
        if(post.getContent().length()>150){
            post.setExcerpt(post.getContent().substring(0,150)+"...");
        } else {
            post.setExcerpt(post.getContent());
        }

        String tagName = tag.getName();
        String[] array = tagName.split(", ");
        for (String name:array){
            tag.addPost(post);
            Tag tagDB = tagRepository.getTagByName(name);
            if(tagDB == null){
                Tag newTag = new Tag();
                newTag.setName(name);
                newTag.setCreatedAt(date);
                post.addTag(newTag);
            } else {
                post.addTag(tagDB);
            }
        }
        postRepository.save(post);
    }

    @Override
    public void saveNewPost(Post post, Tag tag) {
        Date date = new Date(System.currentTimeMillis());
        post.setCreatedAt(date);
        postSaver(post,tag);
    }

    @Override
    public void publishNewPost(Post post, Tag tag) {
        Date date = new Date(System.currentTimeMillis());
        post.setCreatedAt(date);
        post.setPublished(true);
        post.setPublishedAt(date);
        postSaver(post,tag);
    }

    @Override
    public void saveUpdatePost(Post post, Tag tag) {
        Post oldPost = getPostById(post.getId());
        Date createDate = oldPost.getCreatedAt();
        Date publishDate = oldPost.getPublishedAt();
        Date date = new Date(System.currentTimeMillis());
        post.setAuthor(oldPost.getAuthor());
        post.setUpdatedAt(date);
        post.setCreatedAt(createDate);
        post.setPublishedAt(publishDate);
        postSaver(post,tag);
    }

    @Override
    public void publishUpdatePost(Post post, Tag tag) {
        Post oldPost = getPostById(post.getId());
        Date createDate = oldPost.getCreatedAt();
        Date publishDate = oldPost.getPublishedAt();
        Date date = new Date(System.currentTimeMillis());
        post.setAuthor(oldPost.getAuthor());
        post.setUpdatedAt(date);
        post.setPublished(true);
        post.setCreatedAt(createDate);
        if(publishDate == null){
            post.setPublishedAt(date);
        } else {
            post.setPublishedAt(publishDate);
        }
        postSaver(post,tag);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String keyword, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize, sort);
        if(keyword != null){
            return postRepository.findPaginatedBySearch(pageable,keyword);
        }
        return postRepository.findPaginatedAll(pageable);
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getFilteredPost(List<Integer> filterTags, List<Post> searchedPostList) {
        List<Post> filteredPostList = new java.util.ArrayList<>(Collections.emptyList());
        if(filterTags.isEmpty()){
            return searchedPostList;
        }
        for(Integer tagId : filterTags){
            Tag tag = tagService.getTagById(tagId);
            List<Post> postByTag = tag.getPosts();
            for(Post post : postByTag){
                if(!filteredPostList.contains(post) && post.isPublished() && searchedPostList.contains(post)){
                    filteredPostList.add(post);
                }
            }
        }
        return filteredPostList;
    }

    @Override
    public String getFilterString(List<Integer> filterTags) {
        String filterString = "";
        if(!filterTags.isEmpty()){
            filterString = "&tagId=" + filterTags.get(0);
            for(int i = 1; i<filterTags.size();i++){
                filterString += "&tagId=" + filterTags.get(i);
            }
        }
        return filterString;
    }


    @Override
    public List<Post> getAllPosts(String keyword) {
        if(keyword != null){
            return postRepository.findAllBySearch(keyword);
        }
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;
        if(optional.isPresent()){
            post = optional.get();
        } else {
            throw new RuntimeException("Post not found with id : " +id);
        }
        return post;
    }
}
