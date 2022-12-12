package com.example.UserCRUD.controller;

import com.example.UserCRUD.models.User;
import com.example.UserCRUD.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"${fems.url}"}, allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserController() {
    }

    @GetMapping(params = {"username"})
    public User getUserByName(@RequestParam("username") String username) {
        return userServiceImpl.getUserByName(username);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userServiceImpl.getUserById(id);
    }

    @PostMapping
    public User saveUser(@RequestParam int id,
                         @RequestParam String username,
                         @RequestParam String profileImg){
        return userServiceImpl.saveUser(id, username, profileImg);
    }
}


