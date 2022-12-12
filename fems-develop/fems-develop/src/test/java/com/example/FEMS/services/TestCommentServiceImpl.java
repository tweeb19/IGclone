package com.example.FEMS.services;

import com.example.FEMS.clients.CommentFeignClient;
import com.example.FEMS.models.Comment;
import com.example.FEMS.models.PageOfItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class TestCommentServiceImpl {

    @Mock
    CommentFeignClient cfc;

    @InjectMocks
    CommentServiceImpl csi;

    PageOfItems<Comment> comment;
    int pageNumber = 1;
    int pageSize = 1;
    int postId = 1;

    @BeforeEach
    public void init(){
        Comment testComment = new Comment(1,1,"userName","description",
                LocalDate.parse("2022-04-26"));
        ArrayList<Comment> arrComment = new ArrayList<Comment>(){{add(testComment);}};

        comment = new PageOfItems<>(arrComment, false, 1);
    }

    @Test
    void getComment(){
        Mockito.when(cfc.getAllComments(postId, pageNumber, pageSize)).thenReturn(comment);
        PageOfItems<Comment> pageOfItems = csi.getAllComments(postId, pageNumber, pageSize);

        assertEquals(pageOfItems, comment);
    }


    @Test
    void getComment_postIdNotNegative(){
        PageOfItems<Comment> pageOfItems = csi.getAllComments(-1, pageNumber, pageSize);
        assertEquals(new PageOfItems<Comment>(), pageOfItems);
    }

    @Test
    void getPost_PageNumberNotNegative(){
        PageOfItems<Comment> pageOfItems = csi.getAllComments(postId, -1 , pageSize);
        assertEquals(new PageOfItems<Comment>(), pageOfItems);
    }

    @Test
    void getPost_PageSizeNotNegative(){
        PageOfItems<Comment> pageOfItems = csi.getAllComments(postId, pageNumber, -1);
        assertEquals(new PageOfItems<Comment>(), pageOfItems);
    }

    @Test
    void getPost_CheckNull(){
        Mockito.when(cfc.getAllComments(postId, pageNumber, pageSize)).thenReturn(null);
        PageOfItems<Comment> pageOfItems = csi.getAllComments(postId, pageNumber, pageSize);

        assertEquals(pageOfItems, new PageOfItems<Comment>());
    }
@Test
    void saveComments(){
        Comment comment1 = new Comment();
        comment1.getCreatedOn();

        if (comment1 == null){
            System.out.println("field submitted is blank");
        }
        else {
            System.out.println(comment1);
        }
}



}
