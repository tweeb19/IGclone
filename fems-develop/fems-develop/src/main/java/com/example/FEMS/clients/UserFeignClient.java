package com.example.FEMS.clients;

import com.example.FEMS.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="user-crud", url = "${user.url}")
public interface UserFeignClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable int id);

    @GetMapping(value = "/users", params = {"username"})
    User getUserByName(@RequestParam("username") String username);

    @PostMapping("/users")
    User addUser(@RequestParam int id, @RequestParam String username, @RequestParam String profileImg);
}
