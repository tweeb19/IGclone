package com.example.FEMS.services;

import com.example.FEMS.clients.PostFeignClient;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
class TestPostServiceImpl {

    @Mock
    PostFeignClient pfc;

    @InjectMocks
    PostServiceImpl psi;

    PageOfItems<Post> post;
    int pageNumber = 1;
    int pageSize = 5;


    @BeforeEach
    public void init(){
        Post testPost = new Post(1,1,"img","description",
                LocalDate.parse("2022-04-26"));
        ArrayList<Post> arrPost = new ArrayList<Post>(){{add(testPost);}};

        post = new PageOfItems<>(arrPost, false, 5);
    }

    @Test
     void getPost(){
        Mockito.when(pfc.getPost(pageNumber, pageSize)).thenReturn(post);
        PageOfItems<Post> pageOfItems = psi.getPost(pageNumber, pageSize);

        assertEquals(pageOfItems, post);
    }

    @Test
    void getPost_PageNumberNotNegative(){
        PageOfItems<Post> pageOfItems = psi.getPost(-1, pageSize);
        assertEquals(new PageOfItems<Post>(), pageOfItems);
    }

    @Test
    void getPost_PageSizeNotNegative(){
        PageOfItems<Post> pageOfItems = psi.getPost(pageNumber, -1);
        assertEquals(new PageOfItems<Post>(), pageOfItems);
    }

    @Test
    void getPost_CheckNull(){
        Mockito.when(pfc.getPost(pageNumber, pageSize)).thenReturn(null);
        PageOfItems<Post> pageOfItems = psi.getPost(pageNumber, pageSize);

        assertEquals(pageOfItems, new PageOfItems<Post>());
    }
}
