package com.group12.rest2night.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.MovieService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    void testGetMovie() {
        // Setup
        int movieId = 123;
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        when(movieService.findMovie(movieId)).thenReturn(Optional.of(movie));

        // Execution
        ResponseEntity<Optional<Movie>> response = movieController.getMovie(movieId);

        // Assertions (Oracles)
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check status code
        assertTrue(response.getBody().isPresent()); // Check if Movie is present
        assertEquals(movie, response.getBody().get()); // Check if correct Movie is returned
    }

    @Test
    void testAddComment() {
        // Setup
        int movieId = 123;
        Comment comment = new Comment();
        comment.setBody("Test comment");

        // Execution
        ResponseEntity<String> response = movieController.addComment(movieId, comment);

        // Assertions (Oracles)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment was successfully added to Movie", response.getBody());
        verify(movieService, times(1)).addComment(comment, movieId); // Verify service call
    }
}
