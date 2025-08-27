package com.group12.rest2night.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {
    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    public void testGetMovies_NoGenderKey() throws IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("age", "30");
        args.put("occupation", "doctor");
        args.put("genre", "action");
        
        try {
            recommendationService.getMovies(args);
            fail("Expected RuntimeErrorException");
        } catch (RuntimeException e) {
            assertEquals("Gender key is not given\n", e.getMessage());
        }
    }

    @Test
    public void testGetMovies_NoAgeKey() throws IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "male");
        args.put("occupation", "doctor");
        args.put("genre", "action");
        
        try {
            recommendationService.getMovies(args);
            fail("Expected RuntimeErrorException");
        } catch (RuntimeException e) {
            assertEquals("Age key is not given\n", e.getMessage());
        }
    }

    @Test
    public void testGetMovies_NoOccupationKey() throws IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "male");
        args.put("age", "30");
        args.put("genre", "action");
        
        try {
            recommendationService.getMovies(args);
            fail("Expected RuntimeErrorException");
        } catch (RuntimeException e) {
            assertEquals("Occupation key is not given\n", e.getMessage());
        }
    }

    @Test
    public void testGetMovies_NoGenreKey() throws IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "male");
        args.put("age", "30");
        args.put("occupation", "doctor");
        
        try {
            recommendationService.getMovies(args);
            fail("Expected RuntimeErrorException");
        } catch (RuntimeException e) {
            assertEquals("Genre key is not given\n", e.getMessage());
        }
    }

    @Test
    public void testGetMovies_EmptyGenres() {
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "male");
        args.put("age", "30");
        args.put("occupation", "doctor");
        args.put("genre", "");
        
        try {
            recommendationService.getMovies(args);
        } catch (RuntimeException e) {
            assertEquals("No movie found that satisfies requested genres", e.getMessage());
        }
    }

    @Test
    public void testGetMovies_NoMoviesFound() {
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "male");
        args.put("age", "30");
        args.put("occupation", "doctor");
        args.put("genre", "action");

        // Mocking behavior to return empty set of movies
        Mockito.when(movieService.getMoviesWith(Mockito.anyList())).thenReturn(new ArrayList<>());

        try {
            recommendationService.getMovies(args);
            fail("Expected RuntimeErrorException");
        } catch (RuntimeException e) {
            assertEquals("No movie found that satisfies requested genres", e.getMessage());
        }
    }

    @Test
    public void testGetMoviesWihtGetTopN(){
        HashMap<String, String> args = new HashMap<>();
        args.put("gender", "F");
        args.put("age", "25");
        args.put("occupation", "scientist");
        args.put("genre", "Action|War");

        List<Integer> someSet = new ArrayList<>();
        someSet.add(5);
        ArrayList<Integer> someArray = new ArrayList<>();
        someArray.add(5);

        ObjectId someId = new ObjectId();
        Rating rating = new Rating(someId, 5, 5, 5, 1618548376);
        List<Rating> listOfRatings = new ArrayList<>();
        listOfRatings.add(rating);

        Mockito.when(movieService.getMoviesWith(Mockito.anyList())).thenReturn(someSet);
        Mockito.when(userService.getUsersWith(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(someArray);
        Mockito.when(userService.getRatingsOfUser(Mockito.anyInt())).thenReturn(listOfRatings);

        List<Movie> movies = recommendationService.getMovies(args);

        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

    @Test
    public void testGetMoviesOnOccasion(){
        assertThrows(RuntimeException.class, () -> recommendationService.getMoviesOnOccasion("familyNight"));
        assertThrows(RuntimeException.class, () -> recommendationService.getMoviesOnOccasion("dateNight"));
        assertThrows(RuntimeException.class, () -> recommendationService.getMoviesOnOccasion("noOccasion"));
    }
    
}
