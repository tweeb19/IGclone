package com.example.FEMS.services;


import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import com.example.FEMS.models.PostFrontEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostFrontEndServiceImpl implements PostFrontEndService {

    @Autowired
    private PostServiceImpl postServiceImpl;

    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public PageOfItems<PostFrontEnd> getAll(int pageNumber, int pageSize) {

        //appends a comment to a post
        if(pageNumber < 0 || pageSize < 0){
            return new PageOfItems<>();
        }

        //store pageofitems comment inside of a postfrontend model
        PageOfItems<Post> posts = postServiceImpl.getPost(pageNumber, pageSize);

        // make a list of postfrontend
        List<PostFrontEnd> postfrontendlist = new ArrayList<>();
        posts.getItems().forEach(post ->
            postfrontendlist.add(new PostFrontEnd(post.getId(), userServiceImpl.getUserById(post.getUserId()), post.getImg(), post.getDescription(), post.getCreatedOn(),
                    commentServiceImpl.getAllComments(post.getId(), 0, 5))));

        return new PageOfItems<>(postfrontendlist, posts.isHasNext(), posts.getTotalElements());
    }
}
