package com.example.UserCRUD.services;

import com.example.UserCRUD.models.User;

public interface UserService {
    User getUserByName(String username);

    User saveUser(int id, String username, String profileImg);

    User getUserById(int id);
}
