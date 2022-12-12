package com.example.PostCRUD.controller;


import com.example.PostCRUD.models.PageOfItems;
import com.example.PostCRUD.models.Post;
import com.example.PostCRUD.models.PostModel;
import com.example.PostCRUD.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${fems.url}", allowCredentials = "true")
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postServiceImpl;

    public PostController() {}

    @GetMapping()
    public PageOfItems<Post> findPaginated(@RequestParam("pageNumber") int page,
                                           @RequestParam ("pageSize") int size) {

        return postServiceImpl.getAllPosts(page, size);
    }

    @PostMapping()
    public Post addPost(@RequestBody PostModel postModel){
        Post post = new Post();
        post.setCreatedOn(postModel.getCreatedOn());
        post.setDescription(postModel.getDescription());
        post.setImg(postModel.getImg());
        post.setUserId(postModel.getUserId());
        return postServiceImpl.addPost(post);
    }
}

