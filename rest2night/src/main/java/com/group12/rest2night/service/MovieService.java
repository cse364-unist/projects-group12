package com.group12.rest2night.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.repository.MovieRepository;
import com.group12.rest2night.repository.RatingRepository;

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

    public Movie addComment(Comment comment, int id){
        Movie movie = findMovie(id).orElse(null);
        List<Comment> comments = movie.getComments();
        if(comments == null){
            List<Comment> new_comments = new ArrayList<>();
            new_comments.add(comment);
            movie.setComments(new_comments);
        } else {
            comments.add(comment);
            movie.setComments(comments);
        }
        return movieRepository.save(movie);
    }
}
