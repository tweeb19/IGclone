package com.example.CommentCRUD.repositories;

import com.example.CommentCRUD.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByPostId(int postId, Pageable pageable);
}
