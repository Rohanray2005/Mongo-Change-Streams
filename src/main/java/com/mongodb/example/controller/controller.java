package com.mongodb.example.controller;

import com.mongodb.example.models.User;
import com.mongodb.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public User getUser() {
        User user = new User();
        user.setAge(10);
        user.setName("dummy");
        return userRepository.save(user);
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
