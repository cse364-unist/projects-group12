package com.group12.rest2night.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.repository.MovieRepository;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;


    public Optional<Movie> findMovie(int id){
        return movieRepository.findByMovieId(id);
    }

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public void addComment(Comment comment, int id){
        comment.setTimestamp(LocalDateTime.now());
        Movie movie = findMovie(id).orElse(null);
        List<Comment> comments = movie.getComments();
        if(comments == null){
            comments = new ArrayList<>();
        } 
        comments.add(comment);
        movie.setComments(comments);
        movieRepository.save(movie);
        return;
    }

    public HashSet<Integer> getMoviesWith(List<String> genres){
        HashSet<Integer> list = new HashSet<>();
        movieRepository.findAll().stream()
                                .filter(movie -> movie.getGenres().stream().anyMatch(genre -> genres.contains(genre.toLowerCase())))
                                .map(Movie::getMovieId)
                                .forEach(list::add);
        return list;
    }
}
