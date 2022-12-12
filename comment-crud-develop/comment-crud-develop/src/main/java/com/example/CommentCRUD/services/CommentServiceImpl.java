package com.example.CommentCRUD.services;

import com.example.CommentCRUD.models.Comment;
import com.example.CommentCRUD.models.CommentModel;
import com.example.CommentCRUD.models.PageOfItems;
import com.example.CommentCRUD.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    CommentServiceImpl(){
    }

    public void setCommentRepository(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    @Override
    public Comment addComment(CommentModel comment){
        Comment comment1 = new Comment(comment.getPostId(), comment.getUsername(), comment.getBody(), comment.getCreatedOn());
        commentRepository.save(comment1);
        return comment1;
    }
    @Override
    public PageOfItems<Comment> getComments(int postId, int pageNumber, int pageSize) {
        Page<Comment> comments = commentRepository.findAllByPostId(postId, PageRequest.of(pageNumber, pageSize, Sort.by("id").descending()));
        PageOfItems<Comment> pageOfItems = new PageOfItems<>();
        pageOfItems.setItems(comments.getContent());
        pageOfItems.setTotalElements((int)comments.getTotalElements());
        pageOfItems.setHasNext(comments.hasNext());
        return pageOfItems;
    }
}
