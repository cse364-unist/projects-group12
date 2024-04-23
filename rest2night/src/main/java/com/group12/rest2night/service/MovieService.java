package com.group12.rest2night.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
