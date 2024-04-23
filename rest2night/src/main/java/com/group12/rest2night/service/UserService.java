package com.group12.rest2night.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.group12.rest2night.entity.User;

import com.group12.rest2night.repository.UserRepository;
import org.bson.types.ObjectId;
import java.util.List;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addMovieToWishlist(ObjectId userId, int movieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getWl().add(movieId);
        userRepository.save(user);
    }

    public List<Integer> getWishlistByUserId(ObjectId userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getWl();
    }
}
