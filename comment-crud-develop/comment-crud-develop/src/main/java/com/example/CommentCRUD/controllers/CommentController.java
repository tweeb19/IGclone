package com.example.CommentCRUD.controllers;



import com.example.CommentCRUD.models.Comment;
import com.example.CommentCRUD.models.CommentModel;
import com.example.CommentCRUD.models.PageOfItems;
import com.example.CommentCRUD.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"${fems.url}"}, methods = RequestMethod.GET)
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    public CommentController(){ }

    @GetMapping
    public PageOfItems getAllComments(@RequestParam("postId") int postId, @RequestParam("pageNumber") int pageNumber,
                                      @RequestParam("pageSize") int pageSize){
        return commentService.getComments(postId, pageNumber, pageSize);
    }
    @PostMapping
    public Comment save(@RequestBody CommentModel comment){
        return commentService.addComment(comment);
    }
}
