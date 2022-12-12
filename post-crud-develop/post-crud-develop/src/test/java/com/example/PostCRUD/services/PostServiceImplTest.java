package com.example.PostCRUD.services;

import com.example.PostCRUD.models.PageOfItems;
import com.example.PostCRUD.models.Post;
import com.example.PostCRUD.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    @Spy
    private PostServiceImpl postService;

    @Mock
    private PostRepository repository;

    @Test
    void addPostRepositoryTest() {
        //given
        Post postTest = new Post();
        postTest.setUserId(3);
        postTest.setDescription("des");
        postTest.setImg("myimage.jpg");
        postTest.setCreatedOn(LocalDate.now());

        postService.addPost(postTest);

        verify(repository, times(1)).save(any());
    }

    @Test
    void addPost() {
        Post postTest = new Post(3, 1, "thirdPost.jpg", "my third post", LocalDate.of(2022, 2, 22));

        Post testPost = new Post();

        Mockito.doReturn(testPost).when(postService).addPost(postTest);
        //System.out.println(postService.addPost());

        //ACT
        Post result = postService.addPost(postTest);
        System.out.println(result);

        //Affirm
        assertEquals(postTest.getUserId(), 1);
    }


    @Test
    void getAllPosts() {
        // Arrange
        int pageNum = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Post postTest = new Post(1, 1, "firstPost.jpg", "my first post", LocalDate.of(2022, 2, 20));
        Post postTest2 = new Post(2, 1, "secondPost.jpg", "my second post", LocalDate.of(2022, 2, 21));
        Post postTest3 = new Post(3, 1, "thirdPost.jpg", "my third post", LocalDate.of(2022, 2, 22));
        Post postTest4 = new Post(4, 1, "fourthPost.jpg", "my fourth post", LocalDate.of(2022, 2, 23));
        Post postTest5 = new Post(5, 1, "fifthPost.jpg", "my fifth post", LocalDate.of(2022, 2, 24));
        List<Post> postList = new ArrayList<Post>(){{add(postTest); add(postTest2); add(postTest3); add(postTest4); add(postTest5);}};

        Page<Post> testPage = new PageImpl<>(postList, pageable, pageSize);
        Mockito.doReturn(testPage).when(postService).getPostPage(pageNum, pageSize);

        // Act
        PageOfItems<Post> result = postService.getAllPosts(pageNum, pageSize);


        // Assert
        assertEquals(result.getTotalElements(), 5);
        assertFalse(result.isHasNext());

    }

    @Test
    void getUserPage() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        Page<Post> postPage = Mockito.mock(Page.class);
        when(repository.findAll(PageRequest.of(pageNum,pageSize, Sort.by("id").descending()))).thenReturn(postPage);

        // Act
        Page<Post> result = postService.getPostPage(0,10);

        //Assert
        assertEquals(postPage.getTotalPages(), result.getTotalPages());
    }
}