package com.example.FEMS.clients;


import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="post-crud", url = "${post.url}")
public interface PostFeignClient {
    @GetMapping("/posts?pageNumber={pageNumber}&pageSize={pageSize}")
    PageOfItems<Post> getPost(@PathVariable int pageNumber,
                              @PathVariable int pageSize);

    @PostMapping("/posts")
    Post addPost(@RequestBody Post post);
}
