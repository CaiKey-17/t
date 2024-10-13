package com.example.Rest_API.controller;


import com.example.Rest_API.model.User;
import com.example.Rest_API.service.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/test")
public class controllerUser {

    private serviceImpl service;

    public controllerUser(serviceImpl service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.listUser();
    }
    @PostMapping("/google-signin")
    public User googleSignIn(@RequestBody User user) {
        return service.addUserProc(user);
    }
    @GetMapping("/check-email")
    public ResponseEntity<User> checkEmail(@RequestParam String email) {
        User existingUser = service.findUser(email);
        if (existingUser != null) {
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public User addUser(@RequestParam("name") String name,
                        @RequestParam("email") String email,
                        @RequestParam("image") MultipartFile imageFile) {
        // Save the image to a folder
        String folder = "src/main/resources/static/images/";
        String imageFileName = imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes()); // Save the image
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a new user object
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setImage("/images/" + imageFileName); // Store the relative path to the image

        // Save the user using the service
        return service.addUserProc(newUser);
    }



}

