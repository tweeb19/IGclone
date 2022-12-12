package com.example.FEMS.services;

import com.example.FEMS.clients.UserFeignClient;
import com.example.FEMS.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserFeignClient userFeignClient;


    @Override
    public User getUserById(int id) {

        return userFeignClient.getUserById(id);
    }

    @Override
    public User getUserByName(String username) {
        return userFeignClient.getUserByName(username);
    }

    @Override
    public User addUser(int id, String username) {

        return userFeignClient.addUser(id, username, "");
    }
}
