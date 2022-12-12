package com.example.FEMS.services;

import com.example.FEMS.models.User;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserService {
    User getUserByName(String username);
    User getUserById(int id);
    User addUser(int id, String username);
}
