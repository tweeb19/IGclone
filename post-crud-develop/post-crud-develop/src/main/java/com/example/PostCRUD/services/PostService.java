package com.example.PostCRUD.services;

import com.example.PostCRUD.models.PageOfItems;
import com.example.PostCRUD.models.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    PageOfItems<Post> getAllPosts(int pageNum, int pageSize);
    Page<Post> getPostPage(int pageNum, int pageSize);
    Post addPost(Post post);

}

