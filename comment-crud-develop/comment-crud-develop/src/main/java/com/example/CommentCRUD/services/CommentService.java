package com.example.CommentCRUD.services;

import com.example.CommentCRUD.models.Comment;
import com.example.CommentCRUD.models.CommentModel;
import com.example.CommentCRUD.models.PageOfItems;

import java.time.LocalDate;

public interface CommentService {
    PageOfItems<Comment> getComments(int postId, int pageNumber, int pageSize);
    Comment addComment(CommentModel comment);
}
