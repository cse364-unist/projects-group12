package com.group12.rest2night.service;

import java.time.LocalDateTime;
import java.util.*;

import com.group12.rest2night.entity.Rating;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.repository.RatingRepository;
import com.group12.rest2night.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.repository.MovieRepository;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Movie> findMovie(int id){
        return movieRepository.findByMovieId(id);
    }

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> someMovies(){
        List<Movie> movies = movieRepository.findAll();
        Random random = new Random();
        int randomInd = random.nextInt(movies.size() - 11);
        return movies.subList(randomInd, randomInd + 10);
    }

    public void addComment(Comment comment, int id){
        comment.setTimestamp(LocalDateTime.now());
        Movie movie = findMovie(id).orElse(null);
        List<Comment> comments = movie.getComments();
        if(comments == null){
            comments = new ArrayList<>();
        }
        ArrayList<Double> rate = movie.getRate();
        rate.set(0, (rate.get(0)*rate.get(1) + comment.getRate())/(rate.get(1)+1));
        rate.set(1, rate.get(1) + 1);
        movie.setRate(rate);
        addNewRating(id, comment.getRate(), comment.getUsername());
        comments.add(comment);
        movie.setComments(comments);
        movieRepository.save(movie);
        return;
    }

    private void addNewRating(int movieId, int rate, String username){
        int userId = -1;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            userId = optionalUser.get().getUserId();
        }
        Rating rating = new Rating();
        rating.setMovieId(movieId);
        rating.setUserId(userId);
        rating.setRating(rate);
        rating.setTimestamp((int)new Date().getTime()/1000);

        ratingRepository.save(rating);
    }

    public List<Integer> getMoviesWith(List<String> genres) {
        return movieRepository.findByGenresIn(genres).stream()
                .map(Movie::getMovieId)
                .toList();
    }

    public List<Rating> getRatingsOfMovie(int movieId){
        return ratingRepository.findByMovieId(movieId);
    }

}
