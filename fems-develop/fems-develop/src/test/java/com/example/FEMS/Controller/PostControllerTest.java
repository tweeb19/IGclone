package com.example.FEMS.Controller;

import com.example.FEMS.models.Comment;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.PostFrontEnd;
import com.example.FEMS.models.User;
import com.example.FEMS.services.CommentServiceImpl;
import com.example.FEMS.services.PostFrontEndService;
import com.example.FEMS.services.PostFrontEndServiceImpl;
import com.example.FEMS.services.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@WebMvcTest(PostController.class)
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PostFrontEndServiceImpl postFrontEndService;

    @MockBean
    CommentServiceImpl commentService;

    @MockBean
    PostServiceImpl postService;

    @Test
    void findPaginated() throws Exception {

        PageOfItems<Comment> commentPageOfItems = getCommentPageOfItems();
        PageOfItems<PostFrontEnd> pageOfItems = getPostFrontEndPageOfItems(commentPageOfItems);
        given(postFrontEndService.getAll(Mockito.anyInt(), Mockito.anyInt())).willReturn(pageOfItems);
        mvc.perform(MockMvcRequestBuilders.get("/posts?pageNumber=1&pageSize=1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.hasNext", is(true)))
                .andExpect(jsonPath("$.totalElements", is(9)))
                .andExpect(jsonPath("$.items[0].description", is("test")))
                .andExpect(jsonPath("$.items[0].img", is("test")))
                .andExpect(jsonPath("$.items[0].id", is(1)))
                .andExpect(jsonPath("$.items[0].user.id", is(1)))
                .andExpect(jsonPath("$.items[0].comment.items[0].id", is(1)))
                .andExpect(jsonPath("$.items[0].comment.items[0].body", is("This is an example comment.")))
                .andExpect(jsonPath("$.items[0].comment.items[0].postId", is(1)))
                .andExpect(jsonPath("$.items[0].comment.items[0].username", is("user1")));
    }

    private PageOfItems<PostFrontEnd> getPostFrontEndPageOfItems(PageOfItems<Comment> commentPageOfItems) {
        PostFrontEnd pfe = new PostFrontEnd();
        User user = new User(1,"test","");
        pfe.setComment(commentPageOfItems);
        pfe.setDescription("test");
        pfe.setImg("test");
        pfe.setId(1);
        pfe.setUser(user);
        PageOfItems<PostFrontEnd> pageOfItems = new PageOfItems<>();
        List<PostFrontEnd> list = Collections.singletonList(pfe);
        pageOfItems.setItems(list);
        pageOfItems.setHasNext(true);
        pageOfItems.setTotalElements(9);
        return pageOfItems;
    }

    private PageOfItems<Comment> getCommentPageOfItems() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setPostId(1);
        comment.setBody("This is an example comment.");
        comment.setUsername("user1");
        PageOfItems<Comment> commentPageOfItems = new PageOfItems<>();
        commentPageOfItems.setItems(Collections.singletonList(comment));
        commentPageOfItems.setHasNext(true);
        commentPageOfItems.setTotalElements(15);
        return commentPageOfItems;
    }

    @Test
    void commentPageOfItems() throws Exception {
        given(commentService.getAllComments(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).willReturn(getCommentPageOfItems());
        mvc.perform(MockMvcRequestBuilders.get("/posts/1/comments?pageNumber=1&pageSize=1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.items[0].id", is(1)))
                .andExpect(jsonPath("$.items[0].body", is("This is an example comment.")))
                .andExpect(jsonPath("$.items[0].postId", is(1)))
                .andExpect(jsonPath("$.items[0].username", is("user1")));
    }

    @Test
    void saveComments() throws Exception{
        Comment comment = new Comment();
        comment.setPostId(1005);
        comment.setBody("test");
        comment.setUsername("test");
        comment.setId(10);
        given(commentService.saveComments(Mockito.any(Comment.class))).willReturn(comment);
        mvc.perform(MockMvcRequestBuilders.post("/posts/comments?postId=1005").content("{" +
                "  \"postId\": 1,\n" +
                "            \"username\": \"test\",\n" +
                "            \"body\": \"description\",\n" +
                "            \"createdOn\": \"2022-05-10\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username", is("test")))
                .andExpect(jsonPath("$.postId", is(1005)))
                .andExpect(jsonPath("$.body", is("test")))
                .andExpect(jsonPath("$.id", is(10)));
        ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).saveComments(argumentCaptor.capture());
        assert(argumentCaptor.getValue().getPostId() == 1005);
    }
}