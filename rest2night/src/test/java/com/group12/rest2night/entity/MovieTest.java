package com.group12.rest2night.entity;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {

    @Test
    public void testMovieConstructorAndGetters() {
        ObjectId id = new ObjectId();
        int movieId = 123;
        String title = "Test Movie";
        int year = 2022;
        List<String> genres = Arrays.asList("Action", "Adventure");
        ArrayList<Double> rate = new ArrayList<>();
        rate.add(4.5); rate.add(100.0);
        List<Comment> comments = new ArrayList<>();
        String poster = "https://somewhere";

        Movie movie = new Movie(id, movieId, title, year, genres, poster, rate, comments);

        assertEquals(id, movie.getId());
        assertEquals(movieId, movie.getMovieId());
        assertEquals(title, movie.getTitle());
        assertEquals(year, movie.getYear());
        assertEquals(genres, movie.getGenres());
        assertEquals(rate, movie.getRate());
        assertEquals(comments, movie.getComments());
    }

    @Test
    public void testMovieSetters() {
        Movie movie = new Movie();
        ObjectId id = new ObjectId();
        int movieId = 123;
        String title = "Test Movie";
        int year = 2022;
        List<String> genres = Arrays.asList("Action", "Adventure");
        ArrayList<Double> rate = new ArrayList<>();
        rate.add(4.5); rate.add(100.0);
        List<Comment> comments = new ArrayList<>();

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
    public void testEqualsAndHashCode() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        int movieId1 = 123;
        int movieId2 = 456;
        String title1 = "Test Movie 1";
        String title2 = "Test Movie 2";
        int year1 = 2022;
        int year2 = 2023;
        List<String> genres1 = Arrays.asList("Action", "Adventure");
        List<String> genres2 = Arrays.asList("Drama", "Romance");
        ArrayList<Double> rate1 = new ArrayList<>();
        rate1.add(4.5); rate1.add(100.0);
        ArrayList<Double> rate2 = new ArrayList<>();
        rate2.add(3.8); rate2.add(150.0);
        List<Comment> comments1 = new ArrayList<>();
        List<Comment> comments2 = new ArrayList<>();
        String poster = "https://somewherer";

        Movie movie1 = new Movie(id1, movieId1, title1, year1, genres1, poster, rate1, comments1);
        Movie movie2 = new Movie(id1, movieId1, title1, year1, genres1, poster, rate1, comments1);
        Movie movie3 = new Movie(id2, movieId1, title1, year1, genres1, poster, rate1, comments1);
        Movie movie4 = new Movie(id1, movieId2, title1, year1, genres1, poster, rate1, comments1);
        Movie movie5 = new Movie(id1, movieId1, title2, year1, genres1, poster, rate1, comments1);
        Movie movie6 = new Movie(id1, movieId1, title1, year2, genres1, poster, rate1, comments1);
        Movie movie7 = new Movie(id1, movieId1, title1, year1, genres2, poster, rate1, comments1);
        Movie movie8 = new Movie(id1, movieId1, title1, year1, genres1, poster, rate2, comments1);
        Movie movie9 = new Movie(id1, movieId1, title1, year1, genres1, poster, rate1, comments2);

        assertEquals(movie1, movie2);
        assertEquals(movie1.hashCode(), movie2.hashCode());
        assertEquals(movie1.toString(), movie2.toString());

        assertEquals(movie1.hashCode(), movie1.hashCode()); // Consistency check
        assertEquals(movie1, movie1); // Reflexivity check

        assertEquals(movie1.equals(movie3), false);
        assertEquals(movie1.equals(movie4), false);
        assertEquals(movie1.equals(movie5), false);
        assertEquals(movie1.equals(movie6), false);
        assertEquals(movie1.equals(movie7), false);
        assertEquals(movie1.equals(movie8), false);
        assertEquals(movie1.equals(movie9), true);
    }

    @Test
    public void testNotEquals() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        int movieId1 = 123;
        int movieId2 = 456;
        String title1 = "Test Movie 1";
        String title2 = "Test Movie 2";
        int year1 = 2022;
        int year2 = 2023;
        List<String> genres1 = Arrays.asList("Action", "Adventure");
        List<String> genres2 = Arrays.asList("Drama", "Romance");
        ArrayList<Double> rate1 = new ArrayList<>();
        rate1.add(4.5); rate1.add(100.0);
        ArrayList<Double> rate2 = new ArrayList<>();
        rate2.add(3.8); rate2.add(150.0);
        List<Comment> comments1 = new ArrayList<>();
        List<Comment> comments2 = new ArrayList<>();
        String poster = "https://somewhere";
        Movie movie1 = new Movie(id1, movieId1, title1, year1, genres1, poster, rate1, comments1);
        Movie movie2 = new Movie(id2, movieId2, title2, year2, genres2, poster, rate2, comments2);

        assertEquals(movie1.equals(movie2), false);
    }
}
