package com.example.FEMS.clients;

import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="comment-crud", url = "${comment.url}")
public interface CommentFeignClient {
    @GetMapping("/comments?postId={postId}&pageNumber={pageNumber}&pageSize={pageSize}")
    PageOfItems<Comment> getAllComments(@PathVariable int postId, @PathVariable int pageNumber, @PathVariable int pageSize );
    @PostMapping("/comments")
    Comment save(@RequestBody Comment comment);

}
