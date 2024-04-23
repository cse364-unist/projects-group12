package com.group12.rest2night.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;
import com.group12.rest2night.service.UserService;
import java.util.List;


@RestController
public class WishListController {

    @Autowired
    private UserService userService;

    @PostMapping("/wishlist/add")
    public ResponseEntity<String> addToWishlist(@RequestParam ObjectId userId, @RequestParam int movieId) {
        userService.addMovieToWishlist(userId, movieId);
        return ResponseEntity.ok("Movie added to wishlist successfully");
    }


    @GetMapping("/wishlist")
    public ResponseEntity<List<Integer>> getWishlistByUserId(@RequestParam ObjectId userId) {
        List<Integer> wishlist = userService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }
    
}
