package com.mountblue.blog.restController;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestHomeController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @GetMapping("/posts")
    public List<Post> findFilteredPaginated(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                        @RequestParam(value = "tagId",defaultValue = "") List<Integer> filterTags,
                                        @RequestParam(value = "sortField", defaultValue = "published_at") String sortField,
                                        @RequestParam(value = "order", defaultValue = "asc") String order,
                                        @RequestParam(value = "search", defaultValue = "") String search){
        int pageSize = 10;
        if(filterTags.isEmpty()){
            List<Tag> allTag = tagService.getAllTags();
            for(Tag tag : allTag){
                filterTags.add(tag.getId());
            }
        }
        Page<Post> page = postService.findPaginatedRestApi(pageNo,pageSize,sortField,order,search,filterTags);
        List<Post> postList = page.getContent();
        return postList;
    }
}
