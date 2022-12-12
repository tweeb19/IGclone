package com.example.FEMS.Controller;

import com.example.FEMS.models.User;
import com.example.FEMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "${ui.url}", allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    public UserController(){}

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping(value = "/users", params = {"username"})
    public User getUserByName(@RequestParam("username") String username) {
        return userService.getUserByName(username);
    }

    @PostMapping("/register")
    public User addUser(@RequestParam("id") int id,
                        @RequestParam("username") String username) {
        return userService.addUser(id, username);
    }
}