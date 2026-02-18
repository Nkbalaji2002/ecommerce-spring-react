package com.social.media.controller;

import com.social.media.model.SocialUser;
import com.social.media.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("social/users")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        var response = socialService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody SocialUser socialUser) {
        var response = socialService.saveUssr(socialUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        socialService.deleteUser(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

}
