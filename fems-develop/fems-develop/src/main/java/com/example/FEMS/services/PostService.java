package com.example.FEMS.services;

import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import com.example.FEMS.models.PostFrontEnd;

public interface PostService {
    PageOfItems<Post> getPost(int pageNumber, int pageSize);
    Post addPost(PostFrontEnd post);
}
