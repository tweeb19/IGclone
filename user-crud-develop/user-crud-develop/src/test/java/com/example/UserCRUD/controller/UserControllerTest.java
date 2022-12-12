package com.example.UserCRUD.controller;

import com.example.UserCRUD.models.User;
import com.example.UserCRUD.services.UserServiceImpl;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;




@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @SpyBean
    private UserController userController;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setMocks() throws Exception{
        MockitoAnnotations.openMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(PostController.class).build();
    }

    @Test
    void getUserByName() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users?username=uma")/*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")/*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveUser() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/users?id=30&username=asdf&profileImg=")/*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void controllerTest() {
        UserController uc = new UserController();
        User testUser=new User(1,"uma","profileImage.jpg");
        userController.getUserByName("uma");
        userController.getUserById(1);
        userController.saveUser(30, "asdf", "");

        Mockito.verify(userService).getUserByName("uma");
        Mockito.verify(userService).getUserById(1);
        Mockito.verify(userService).saveUser(30, "asdf", "");
    }

    private void verify(User user) {
    }
}