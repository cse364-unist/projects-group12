package com.group12.rest2night.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> getMovie(@PathVariable int id){
        return new ResponseEntity<Optional<Movie>>(movieService.findMovie(id), HttpStatus.OK);
    }

    
    @PostMapping("/{id}/addComment")
    public ResponseEntity<Movie> addComment(@PathVariable int id, @RequestBody Comment comment){
        return new ResponseEntity<Movie>(movieService.addComment(comment, id), HttpStatus.CREATED);
    }

}
