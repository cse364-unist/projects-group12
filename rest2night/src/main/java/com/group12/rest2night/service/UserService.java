package com.group12.rest2night.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    public void addMovieToWishlist(String username, int movieId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> wishList = user.getWishList();
        if (wishList == null) {
            wishList = new ArrayList<>();
        }
        wishList.add(movieId);
        user.setWishList(wishList);
        userRepository.save(user);
    }

    public List<Movie> getWishlist(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Movie> movies = new ArrayList<>();
        user.getWishList().stream()
                        .forEach(movieId -> {
                            Movie movie = movieService.findMovie(movieId).orElse(null);
                            movies.add(movie);
                        });
        return movies;
    }

    public User register(User user){
        return userRepository.save(user);
    }
    
    public boolean isValidUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPassword().equals(password);
        }
        return false;
    }

    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
