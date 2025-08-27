package com.group12.rest2night.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.repository.RatingRepository;
import com.group12.rest2night.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MongoTemplate mt;

    public void addMovieToWishlist(String username, int movieId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> wishList = user.getWishList();
        if (wishList == null) {
            wishList = new ArrayList<>();
        }
        if(wishList.contains(movieId)){
            return;
        }
        wishList.add(movieId);
        user.setWishList(wishList);
        userRepository.save(user);
    }

    public void addMovieToUnlockedlist(String username, int movieId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> unlockedMovies = user.getUnlockedMovies();
        if (unlockedMovies == null) {
            unlockedMovies = new ArrayList<>();
        }
        if(unlockedMovies.contains(movieId)){
            return;
        }
        unlockedMovies.add(movieId);
        takePointsFromUser(user);
        user.setUnlockedMovies(unlockedMovies);
        userRepository.save(user);
    }

    public void deleteMovieFromWishlist(String username, int movieId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> wishList = user.getWishList();
        if (wishList == null) {
            return;
        }
        wishList.remove(Integer.valueOf(movieId));
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

    public List<Integer> getUnlockedMovies(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUnlockedMovies();
    }

    public List<Movie> getUnlockedMoviesList(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Movie> movies = new ArrayList<>();
        user.getUnlockedMovies().stream()
                .forEach(movieId -> {
                    Movie movie = movieService.findMovie(movieId).orElse(null);
                    movies.add(movie);
                });
        return movies;
    }

    public User register(User user){
        user.setPoints(100);
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

    public List<Integer> getUsersWith(String occup, Integer age, String gender) {
        Query query = new Query();

        if (age != null && age != -1) {
            query.addCriteria(Criteria.where("age").is(age));
        }
        if (gender != null && !gender.isEmpty()) {
            query.addCriteria(Criteria.where("gender").is(gender.toLowerCase()));
        }
        if (occup != null && !occup.isEmpty()) {
            query.addCriteria(Criteria.where("occupation").is(occup.toLowerCase()));
        }

        return mt.find(query, User.class).stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
    }

    public List<Rating> getRatingsOfUser(int userId){
        return ratingRepository.findByUserId(userId);
    }

    public void addPointsToUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        long points = user.getPoints();
        user.setPoints(points + 10);
        userRepository.save(user); 
    }

    public void takePointsFromUser(User user){
        long points = user.getPoints();
        user.setPoints(points - 10);
    }

    public long getPointsOfUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getPoints();
    }
}
