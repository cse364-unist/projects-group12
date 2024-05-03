package com.group12.rest2night.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.repository.RatingRepository;
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

    @Autowired
    private RatingRepository ratingRepository;

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

    public ArrayList<Integer> getUsersWith(String occup, int age, String gender){
        ArrayList<Integer> list = new ArrayList<>();
        userRepository.findAll().stream()
                                    .forEach(user -> {
                                        if((age == -1 || user.getAge() == age) && (gender == "" || user.getGender().toLowerCase().equals(gender)) && (occup == "" || user.getOccupation().toLowerCase().equals(occup))){
                                            list.add(user.getUserId());
                                        };
                                    }
                                    );
        return list;            
    }

    public List<Rating> getRatingsOfUser(int userId){
        return ratingRepository.findByUserId(userId);
    }

    public void addPointsToUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        long points = user.getPoints();
        user.setPoints(points+10);
    }
}
