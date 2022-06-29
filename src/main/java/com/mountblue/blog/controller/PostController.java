package com.mountblue.blog.controller;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.Tag;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

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
//                                        @RequestParam("sortField") String sortField,
//                                        @RequestParam("sortDir") String sortDir,
                                        Model model,
                                        String keyword){
        // [5,6,7,8,9,10,11]
        int pageSize = 5;

        List<Tag> tagList = tagService.getAllTags();
        List<Post> filteredPostList = new java.util.ArrayList<>(Collections.emptyList());
        for(Integer tagId : filterTags){
            Tag tag = tagService.getTagById(tagId);
            List<Post> postByTag = tag.getPosts();
            for(Post post : postByTag){
                if(!filteredPostList.contains(post) && post.isPublished()){
                    filteredPostList.add(post);
                }
            }
        }

//        for (Post post : filteredPostList){
//            System.out.println(post.getTitle());
//        }

        PagedListHolder<Post> pagedListHolder = new PagedListHolder<>(filteredPostList);
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(pageNo-1);
        List<Post> postList = pagedListHolder.getPageList();

//        for (Post post : postList){
//            System.out.println(post.getTitle());
//        }
        String filterString = "";
        if(!filterTags.isEmpty()){
            filterString = "?tagId=" + filterTags.get(0);
            for(int i = 1; i<filterTags.size();i++){
                filterString += "&tagId=" + filterTags.get(i);
            }
            System.out.println(filterString);
        }



        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        model.addAttribute("totalItems", pagedListHolder.getNrOfElements());
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("tagList", tagList);
        model.addAttribute("postList", postList);
        model.addAttribute("filterString", filterString);
//        model.addAttribute("filtertags", filterTags);

        return "filterpage";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
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

    @GetMapping("/newpost")
    public String addNewPost(Model postModel, Model tagModel){
        Tag tag = new Tag();
        Post post = new Post();
        postModel.addAttribute("post", post);
        tagModel.addAttribute("tag", tag);
        return "newpost";
    }

    @PostMapping("/saveNewPost")
    public String saveNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.saveNewPost(post, tag);
        return "redirect:/draftPage";
    }

    @PostMapping("/publishNewPost")
    public String publishNewPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.publishNewPost(post, tag);
        return "redirect:/";
    }

    @PostMapping("/saveUpdatePost")
    public String saveUpdatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.saveUpdatePost(post, tag);
        return "redirect:/draftPage";
    }
    @PostMapping("/publishUpdatePost")
    public String publishUpdatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag){
        postService.publishUpdatePost(post, tag);
        return "redirect:/";
    }

    @GetMapping("/showPost/{id}")
    public String showPost(@PathVariable (value = "id") int id, Model model, Model commentModel){
        Post post = postService.getPostById(id);
        Comment comment = new Comment();
        List<Comment> commentList = commentService.getCommentListByPostId(id);
        model.addAttribute("post", post);
        commentModel.addAttribute("comment", comment);
        commentModel.addAttribute("commentList", commentList);
        return "blogpage";
    }


    @GetMapping("/updatePost/{id}")
    public String updatePost(@PathVariable (value = "id") int id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        Tag postTag = new Tag();
        List<Tag> postTagList = post.getTags();
        Tag tag1 = postTagList.get(0);
        String allTags = tag1.getName();
        for(int i=1; i<postTagList.size();i++){
            Tag tag = postTagList.get(i);
            String name = ", " + tag.getName();
            allTags += name;
        }
        postTag.setName(allTags);
        model.addAttribute("tag",postTag);
        return "updatepost";
    }
}
