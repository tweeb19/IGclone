package com.example.FEMS.Controller;

import com.example.FEMS.models.User;
import com.example.FEMS.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;


import static org.mockito.BDDMockito.given;


@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void test_user_controller() throws Exception {
        User user = new User(1, "test", "test");
        given(userService.getUserById(1)).willReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("test")))
                .andExpect(jsonPath("$.profileImg", is("test")));
    }

    @Test
    public void test_user_controller_name() throws Exception {
        User user = new User(1, "test", "test");
        given(userService.getUserByName("test")).willReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/users?username=test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("test")))
                .andExpect(jsonPath("$.profileImg", is("test")));
    }
}
