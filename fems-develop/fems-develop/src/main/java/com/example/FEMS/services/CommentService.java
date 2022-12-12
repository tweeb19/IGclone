package com.example.FEMS.services;

import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Comment;



public interface CommentService {
    PageOfItems<Comment> getAllComments(int postId, int pageNumber, int pageSize);
}
