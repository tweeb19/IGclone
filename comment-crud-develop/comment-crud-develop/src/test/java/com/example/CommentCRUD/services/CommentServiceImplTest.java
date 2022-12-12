package com.example.CommentCRUD.services;

import com.example.CommentCRUD.models.Comment;
import com.example.CommentCRUD.models.CommentModel;
import com.example.CommentCRUD.models.PageOfItems;
import com.example.CommentCRUD.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl();
        commentService.setCommentRepository(commentRepository);
    }

    @Test
    void getCommentsTest(){

        //set up
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        List<Comment> commentList = Collections.singletonList(new Comment(1, 1, "test", "test", LocalDate.now()));
        Page<Comment> expectedComments = new PageImpl<>(commentList);

        Mockito.when(commentRepository.findAllByPostId(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(expectedComments);

        //Act
        PageOfItems<Comment> comments = commentService.getComments(1, 1, 1);

        //Assert / Verify
        assertEquals(comments.getTotalElements(), 1);
        assertFalse(comments.isHasNext());
        assertEquals(comments.getItems(), commentList);

        Mockito.verify(commentRepository).findAllByPostId(Mockito.anyInt(), captor.capture());
        Pageable pageable1 = captor.getValue();
        assertEquals(pageable1.getPageSize(), 1);
        assertEquals(pageable1.getPageNumber(), 1);

    }
    @Test
    void addComment_savesInDatabase() {
        CommentModel comment = new CommentModel(1, "test", "description", LocalDate.now());
        Comment comment1 = new Comment(comment.getPostId(), comment.getUsername(), comment.getBody(), comment.getCreatedOn());
        commentService.addComment(comment);
        Mockito.verify(commentRepository, times(1)).save(comment1);
    }
}