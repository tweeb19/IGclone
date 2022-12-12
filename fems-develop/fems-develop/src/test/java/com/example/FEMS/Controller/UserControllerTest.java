package com.example.FEMS.Controller;

import com.example.FEMS.models.User;
import com.example.FEMS.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void init(){
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void controller_returns_not_null(){
        User user = new User(1, "test", "test");
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(user);
        assertNotNull(userController.getUser(1));
    }

    @Test
    public void controller_uses_service(){
        User user1 = userController.getUser(1);

        Mockito.verify(userService).getUserById(1);
    }

    @Test
    public void controller_returns_service_value(){
        User user = new User(1, "test", "test");
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(user);

        User user1 = userController.getUser(1);

        assertEquals(user1, user);
    }

    @Test
    public void controller_returns_not_null_name(){
        User user = new User(1, "test", "test");
        Mockito.when(userService.getUserByName("test")).thenReturn(user);
        assertNotNull(userController.getUserByName("test"));
    }

    @Test
    public void controller_uses_service_name(){
        User user1 = userController.getUserByName("test");
        Mockito.verify(userService).getUserByName("test");
    }

    @Test
    public void controller_returns_service_value_name(){
        User user = new User(1, "test", "test");
        Mockito.when(userService.getUserByName("test")).thenReturn(user);

        User user1 = userController.getUserByName("test");

        assertEquals(user1, user);
    }



}