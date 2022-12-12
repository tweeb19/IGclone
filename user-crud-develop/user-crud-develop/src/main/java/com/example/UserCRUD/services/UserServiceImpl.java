package com.example.UserCRUD.services;

import com.example.UserCRUD.models.User;
import com.example.UserCRUD.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User saveUser(int id, String username, String profileImg){
        User user = new User(id, username, profileImg);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}
