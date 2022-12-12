package com.example.FEMS.Controller;

import com.example.FEMS.models.Comment;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import com.example.FEMS.models.PostFrontEnd;
import com.example.FEMS.services.CommentServiceImpl;
import com.example.FEMS.services.PostFrontEndService;
import com.example.FEMS.services.PostService;


import com.example.FEMS.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "${ui.url}", allowCredentials = "true")
public class PostController {

    private PostFrontEndService postFrontendService;
    private CommentServiceImpl commentService;
    private PostServiceImpl postService;

    @Autowired
    public PostController(PostFrontEndService postFrontendService, CommentServiceImpl commentService, PostServiceImpl postService) {
        this.postFrontendService = postFrontendService;
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping
    public PageOfItems<PostFrontEnd> findPaginated(@RequestParam int pageNumber,
                                                   @RequestParam int pageSize) {
        return postFrontendService.getAll(pageNumber, pageSize);
    }

    @GetMapping(value = "/{postId}/comments", params = {"pageNumber", "pageSize"})
    public PageOfItems<Comment> commentPageOfItems(@PathVariable int postId,
                                         @RequestParam int pageNumber,
                                         @RequestParam int pageSize){
        return commentService.getAllComments(postId,pageNumber, pageSize);
    }

    @PostMapping
    public Post addPost(@RequestBody PostFrontEnd post) {
        return postService.addPost(post);
    }

    @PostMapping("/comments")
    public Comment saveComment(@RequestParam int postId, @RequestBody Comment comment){
        comment.setPostId(postId);
        return commentService.saveComments(comment);
    }
}
