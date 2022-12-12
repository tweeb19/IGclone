package com.example.UserCRUD.services;

import com.example.UserCRUD.models.User;
import com.example.UserCRUD.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    User testUser;

    @BeforeEach
    void init() {
        testUser = new User(1, "uma", "profileImage.jpg");
    }


    @Test
    void testGetUserByName() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        User user = userServiceImpl.getUserByName(testUser.getUsername());
        assertEquals(testUser, user);
    }

    @Test
    void testGetUserById() {
        Mockito.when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        User user = userServiceImpl.getUserById(testUser.getId());
        assertEquals(testUser, user);
    }


    @Test
    void testGetUserByIdNull() {
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
        User testNullUser = userServiceImpl.getUserById(2);
        assertNull(testNullUser);
    }

    @Test
    void testSaveUser(){
        User testSaveUser = new User(2, "john", "profileImg.jpg");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testSaveUser);
        User saveUser = userServiceImpl.saveUser(2, "john", "profileImg.jpg");
        assertEquals(testSaveUser, saveUser);
    }
}