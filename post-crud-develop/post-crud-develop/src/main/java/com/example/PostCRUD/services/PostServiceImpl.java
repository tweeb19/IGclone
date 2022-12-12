package com.example.PostCRUD.services;

import com.example.PostCRUD.models.PageOfItems;
import com.example.PostCRUD.models.Post;
import com.example.PostCRUD.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    public PageOfItems<Post> getAllPosts(int pageNum, int pageSize) {
        PageOfItems<Post> pageItems = new PageOfItems<Post>();
        Page<Post> pageResult = getPostPage(pageNum, pageSize);
        pageItems.setItems(pageResult.toList());
        pageItems.setHasNext(pageResult.hasNext());
        pageItems.setTotalElements((int) pageResult.getTotalElements());
        return pageItems;
    }

    public Page<Post> getPostPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());
        return repository.findAll(pageable);
    }



    @Override
    public Post addPost(Post post) {
        return repository.save(post);
    }
}