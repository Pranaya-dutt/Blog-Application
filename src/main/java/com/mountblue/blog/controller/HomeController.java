package com.mountblue.blog.controller;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @GetMapping("/signin")
    public String signin(){
        return "loginpage";
    }

    @GetMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword, @Param("sortDir") String sortDir){
        if(keyword == null){
            keyword = "";
        }
        if(sortDir == null){
            sortDir = "asc";
        }
        return findPaginated(1,"published_at",sortDir,model,keyword);
    }

    @GetMapping("/draftPage")
    public String draftPage(Model model, @Param("keyword") String keyword){
        model.addAttribute("postList", postService.getAllPosts(keyword));
        return "draftspage";
    }

    @GetMapping("/filter/{pageNo}")
    public String findFilteredPaginated(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam(value = "tagId",defaultValue = "") List<Integer> filterTags,
                                        @RequestParam(value = "sortField", defaultValue = "publishedAt") String sortField,
                                        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                        Model model,
                                        @RequestParam(value = "keyword", defaultValue = "") String keyword){
        int pageSize = 5;
        List<Tag> tagList = tagService.getAllTags();
        List<Post> searchedPostList = postService.getAllPosts(keyword);
        List<Post> filteredPostList = postService.getFilteredPost(filterTags,searchedPostList);
        String filterString = postService.getFilterString(filterTags);

        boolean sortOrder;
        if(sortDir.equals("asc")){
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

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        model.addAttribute("totalItems", pagedListHolder.getNrOfElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("tagList", tagList);
        model.addAttribute("postList", postList);
        model.addAttribute("filterString", filterString);
        model.addAttribute("filterTags", filterTags);

        return "filterpage";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                Model model,
                                String keyword){
        int pageSize = 5;
        Page<Post> page = postService.findPaginated(pageNo,pageSize,keyword,sortField,sortDir);
        List<Post> postList = page.getContent();
        List<Tag> tagList = tagService.getAllTags();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("tagList", tagList);
        model.addAttribute("postList", postList);

        return "homepage";
    }
}
