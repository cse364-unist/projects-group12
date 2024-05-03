package com.group12.rest2night.controller;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.MovieService;
import com.group12.rest2night.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.group12.rest2night.entity.LoginRequest;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.RecommendationService;
import com.group12.rest2night.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) // Important for Mockito initialization!
public class RecommendationControllerTest {


    @Mock
    private MovieService movieService;
    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private MovieController movieController;
    @InjectMocks
    private RecommendationController recommendationController;

    @Test
    void testGetMovies() {
        // Mock input
        String input = "{\"genre\":\"action\",\"year\":\"2022\"}";

        // Mock recommendation service response
        List<Movie> recommendedMovies = new ArrayList<>();
        recommendedMovies.add(new Movie());
        recommendedMovies.add(new Movie());

        // Configure recommendationService mock
        when(recommendationService.getMovies(new HashMap<String, String>() {{
            put("genre", "action");
            put("year", "2022");
        }})).thenReturn(recommendedMovies);

        // Call controller method
        ResponseEntity<List<Movie>> responseEntity = recommendationController.getMovies(input);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    public void testGetRecommendationsOnOccasion_Success_FamilyNight() {
        String occasion = "family night";
        List<Movie> expectedMovies = createFamilyNightMovies(); // Use your helper method

        when(recommendationService.getMoviesOnOccasion(occasion)).thenReturn(expectedMovies);

        ResponseEntity<List<Movie>> result = recommendationController.getRecommendationsOnOccasion(occasion);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedMovies, result.getBody());
    }

    private List<Movie> createFamilyNightMovies() {
        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(new ObjectId());
        movie1.setMovieId(1001);
        movie1.setTitle("The Princess Bride"); // Classic fantasy adventure with humor
        movie1.setYear(1987);
        movie1.setGenres(List.of("Fantasy", "Adventure", "Comedy", "Romance"));
        movies.add(movie1);

        Movie movie2 = new Movie();
        movie2.setId(new ObjectId());
        movie2.setMovieId(1002);
        movie2.setTitle("Moana");  // Modern Disney animated adventure
        movie2.setYear(2016);
        movie2.setGenres(List.of("Animation", "Adventure", "Musical", "Fantasy"));
        movies.add(movie2);
        return movies;
    }

}
