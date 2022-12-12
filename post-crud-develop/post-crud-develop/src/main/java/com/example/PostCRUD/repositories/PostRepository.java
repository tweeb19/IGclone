package com.example.PostCRUD.repositories;

import com.example.PostCRUD.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);

}

