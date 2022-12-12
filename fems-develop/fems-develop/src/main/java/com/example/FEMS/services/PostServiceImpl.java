package com.example.FEMS.services;

import com.example.FEMS.clients.PostFeignClient;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import com.example.FEMS.models.PostFrontEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostFeignClient postFeignClient;

    @Override
    public PageOfItems<Post> getPost(int pageNumber, int pageSize) {

        if(pageNumber < 0 || pageSize < 0){
            return new PageOfItems<>();
        }

        PageOfItems<Post> post = postFeignClient.getPost(pageNumber, pageSize);
        if(post == null){
            return new PageOfItems<>();
        }
        return post;
    }

    @Override
    public Post addPost(PostFrontEnd post) {
        System.out.println("post created");
        Post post1 = new Post();
        post1.setUserId(post.getUser().getId());
        post1.setDescription(post.getDescription());
        post1.setImg(post.getImg());
        post1.setCreatedOn(post.getCreatedOn());
        return postFeignClient.addPost(post1);
    }
}
