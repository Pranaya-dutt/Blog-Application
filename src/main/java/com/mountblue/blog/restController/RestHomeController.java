package com.mountblue.blog.restController;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
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
                                        @RequestParam(value = "sortField", defaultValue = "publishedAt") String sortField,
                                        @RequestParam(value = "order", defaultValue = "asc") String order,
                                        @RequestParam(value = "search", defaultValue = "") String search){
        int pageSize = 10;
        List<Post> searchedPostList = postService.getAllPosts(search);
        List<Post> filteredPostList = postService.getFilteredPost(filterTags,searchedPostList);

        boolean sortOrder;
        if(order.equals("asc")){
            sortOrder=true;
        } else {
            sortOrder =false;
        }

        PagedListHolder<Post> pagedListHolder = new PagedListHolder<>(filteredPostList);
        pagedListHolder.setSort(new MutableSortDefinition(sortField,false,sortOrder));
        pagedListHolder.resort();
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(pageNo-1);
        List<Post> postList = pagedListHolder.getPageList();

        return postList;
    }

//    @GetMapping("/posts")
//    public List<Post> findPaginated(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
//                                    @RequestParam(value = "sortField", defaultValue = "published_at") String sortField,
//                                    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
//                                    @Param(value = "search") String search){
//        int pageSize = 10;
//        Page<Post> page = postService.findPaginated(pageNo,pageSize,search,sortField,sortDir);
//        List<Post> postList = page.getContent();
//        return postList;
//    }
}
