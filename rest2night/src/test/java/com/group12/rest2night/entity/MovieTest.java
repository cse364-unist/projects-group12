package com.group12.rest2night.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class MovieTest {

    @Test
    void testSettersAndGetters() {
        Movie movie = new Movie();

        ObjectId id = new ObjectId();
        int movieId = 1234;
        String title = "Kholat";
        int year = 2024;
        List<String> genres = new ArrayList<>();
        genres.add("Comedy");
        double rate = 4.98;
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());

        movie.setId(id);
        movie.setMovieId(movieId);
        movie.setTitle(title);
        movie.setYear(year);
        movie.setGenres(genres);
        movie.setRate(rate);
        movie.setComments(comments);

        assertEquals(id, movie.getId());
        assertEquals(movieId, movie.getMovieId());
        assertEquals(title, movie.getTitle());
        assertEquals(year, movie.getYear());
        assertEquals(genres, movie.getGenres());
        assertEquals(rate, movie.getRate());
        assertEquals(comments, movie.getComments());
    }

    @Test
    void testEqualsAndHashCode() {
        ObjectId someId = new ObjectId();
        Movie movie1 = new Movie(someId, 12345, "Test Movie", 2022, new ArrayList<>(), 4.5, new ArrayList<>());
        Movie movie2 = new Movie(someId, 12345, "Test Movie", 2022, new ArrayList<>(), 4.5, new ArrayList<>());

        assertEquals(movie1, movie2);
        assertEquals(movie1.hashCode(), movie2.hashCode());

        movie2.setTitle("Kholat");

        assertNotEquals(movie1, movie2);
        assertNotEquals(movie1.hashCode(), movie2.hashCode());

        assertNotEquals(movie1, null);
    }

    @Test
    void testToString() {
        Movie movie = new Movie();
        assertNotNull(movie.toString());
    }
}
