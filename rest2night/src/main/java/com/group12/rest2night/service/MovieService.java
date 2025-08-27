package com.group12.rest2night.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.group12.rest2night.entity.Rating;
import com.group12.rest2night.repository.RatingRepository;
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
        comments.add(comment);
        movie.setComments(comments);
        movieRepository.save(movie);
        return;
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
