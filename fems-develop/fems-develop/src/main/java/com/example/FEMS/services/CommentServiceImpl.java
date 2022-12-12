package com.example.FEMS.services;

import com.example.FEMS.clients.CommentFeignClient;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentFeignClient commentFeignClient;

    @Override
    public PageOfItems<Comment> getAllComments(int postId, int pageNumber, int pageSize) {

        if(pageNumber < 0 || pageSize < 0){
            return new PageOfItems<>();
        }

        PageOfItems<Comment> comment = commentFeignClient.getAllComments(postId,pageNumber, pageSize);
        if(comment == null){
            return new PageOfItems<>();
        }
        return comment;
    }

    public Comment saveComments(Comment comment) {

       return commentFeignClient.save(comment);



    }
}
