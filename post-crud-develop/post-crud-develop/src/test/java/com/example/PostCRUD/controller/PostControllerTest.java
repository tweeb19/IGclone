package com.example.PostCRUD.controller;

import com.example.PostCRUD.models.PageOfItems;
import com.example.PostCRUD.models.Post;
import com.example.PostCRUD.services.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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
@WebMvcTest(PostController.class)
@ExtendWith(SpringExtension.class)
class postControllerTest {

    @SpyBean
    private PostController postController;

    @MockBean
    private PostServiceImpl postService;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setMocks() throws Exception{
        MockitoAnnotations.openMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(PostController.class).build();
    }
    @Test
    void addPost() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts").contentType(MediaType.APPLICATION_JSON).content("{  \"userId\": 11,\n" +
                "            \"img\": \"https://th.bing.com/th/id/OIP.yxcekii72eK21_4n-_FZEQHaE8?w=298&h=199&c=7&r=0&o=5&dpr=1.5&pid=1.7\",\n" +
                "            \"description\": \"Parrots eat with their feet.\",\n" +
                "            \"createdOn\": \"2022-04-26\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getAllPosts() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?pageNumber=1&pageSize=3")/*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void controllerTest() {
        PostController pc = new PostController();
        int page = 1;
        int size = 3;
        postController.findPaginated(page, size);
        verify(postService.getAllPosts(1,3));
    }

    private void verify(PageOfItems<Post> posts) {
    }

}
