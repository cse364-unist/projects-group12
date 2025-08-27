package com.group12.rest2night.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group12.rest2night.service.MovieService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public ResponseEntity<?> allMovies(){
        return ResponseEntity.ok(movieService.allMovies());
    }

    @GetMapping("/some")
    public ResponseEntity<?> someMovies(){
        return ResponseEntity.ok(movieService.someMovies());
    }
}
