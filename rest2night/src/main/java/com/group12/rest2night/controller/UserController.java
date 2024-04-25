package com.group12.rest2night.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;

import com.group12.rest2night.entity.LoginRequest;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.service.UserService;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{username}/wishList/add")
    public ResponseEntity<String> addToWishlist(@PathVariable String username, @RequestParam int movieId) {
        userService.addMovieToWishlist(username, movieId);
        return ResponseEntity.ok("Movie added to wishlist successfully");
    }


    @GetMapping("/{username}/wishList")
    public ResponseEntity<?> getWishlistByUserId(@PathVariable String username) {
        List<Movie> wishlist = userService.getWishlist(username);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user){
        String username = user.getUsername();

        if (userService.isUserExists(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (userService.isValidUser(username, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    
}
