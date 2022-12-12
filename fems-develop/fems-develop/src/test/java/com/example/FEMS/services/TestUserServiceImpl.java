package com.example.FEMS.services;

import com.example.FEMS.clients.UserFeignClient;
import com.example.FEMS.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestUserServiceImpl {

    @Mock
    UserFeignClient ufc;

    @InjectMocks
    UserServiceImpl usi;
    User user;
    int id = 1;
    String username = "test";

    @BeforeEach
    public void init(){
        user = new User(1, "john jackson", "");


    }

    @Test
    void getUserById(){
        Mockito.when(ufc.getUserById(id)).thenReturn(user);
        User testUser = usi.getUserById(id);

        assertEquals(testUser, user);
    }

    @Test
    void getUserByName(){
        Mockito.when(ufc.getUserByName(username)).thenReturn(user);
        User testUser = usi.getUserByName(username);

        assertEquals(testUser, user);
    }

    @Test
    void addUser(){
        Mockito.when(ufc.addUser(user.getId(), user.getUsername(), "")).thenReturn(user);
        User testUser = usi.addUser(user.getId(), user.getUsername());

        assertEquals(testUser, user);
    }
}
