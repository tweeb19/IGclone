package com.example.CommentCRUD.controllers;

import com.example.CommentCRUD.models.Comment;
import com.example.CommentCRUD.models.PageOfItems;
import com.example.CommentCRUD.services.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
@ExtendWith(SpringExtension.class)
class CommentControllerTest {

    @SpyBean
    private CommentController commentController;

    @MockBean
    private CommentServiceImpl commentService;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setMocks() throws Exception{
        MockitoAnnotations.openMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(CommentController.class).build();
    }
    @Test
    void getAllComments() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/comments?postId=1&pageNumber=1&pageSize=3")/*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void controllerTest() {
        CommentController cc = new CommentController();
        int page = 1;
        int size = 3;
        commentController.getAllComments(1, page, size);
        verify(commentService.getComments(1,1,3));
    }

    @Test
    void addComment_returnsGoodStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comments").contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                "  \"postId\": 1,\n" +
                "            \"username\": \"test\",\n" +
                "            \"body\": \"description\",\n" +
                "            \"createdOn\": \"2022-05-10\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    private void verify(PageOfItems<Comment> comments) {
    }

}