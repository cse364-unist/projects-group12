package com.group12.rest2night.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.RecommendationService;
import com.group12.rest2night.service.MovieService;
import com.group12.rest2night.entity.Movie;

public class RecommendationControllerTest {

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private RecommendationController recommendationController;


    @Test
    public void testGetMovies_BadRequest() {
        String input = "Invalid JSON";

        ResponseEntity<List<Movie>> result = recommendationController.getMovies(input);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

}

