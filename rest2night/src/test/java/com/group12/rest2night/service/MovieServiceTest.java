package com.group12.rest2night.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group12.rest2night.entity.Comment;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks 
    private MovieService movieService;

    @Test
    void testFindMovie() {

        Movie movie = new Movie();
        movie.setMovieId(1);

        when(movieRepository.findByMovieId(1)).thenReturn(Optional.of(movie));

        Optional<Movie> foundMovie = movieService.findMovie(1);

        assertTrue(foundMovie.isPresent());
        assertEquals(1, foundMovie.get().getMovieId());
    }

    @Test
    void testAllMovies() {

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());

        when(movieRepository.findAll()).thenReturn(movieList);

        List<Movie> allMovies = movieService.allMovies();

        assertEquals(2, allMovies.size());
    }

    // @Test
    // void testAddComment() {

    //     Comment comment = new Comment();
    //     comment.setBody("Test comment");

    //     Movie movie = new Movie();
    //     movie.setMovieId(1);

    //     when(movieRepository.findByMovieId(1)).thenReturn(Optional.of(movie));

    //     movieService.addComment(comment, 1);

    //     assertEquals(1, movie.getComments().size());
    //     verify(movieRepository, times(1)).save(movie);
    // }

    // @Test
    // void testAddCommentToMovieWithComments() {

    //     Comment comment = new Comment();
    //     comment.setBody("Test comment");

    //     Comment comment2 = new Comment();
    //     comment2.setBody("Test2 comment");

    //     Movie movie = new Movie();
    //     movie.setMovieId(1);
    //     List<Comment> comments = new ArrayList<>();
    //     comments.add(comment2);
    //     movie.setComments(comments);

    //     when(movieRepository.findByMovieId(1)).thenReturn(Optional.of(movie));

    //     movieService.addComment(comment, 1);

    //     assertEquals(2, movie.getComments().size());
    //     verify(movieRepository, times(1)).save(movie);
    // }

    @Test
    void testGetMoviesWith(){
        Movie movie = new Movie(new ObjectId(), 5, "Test Movie", 2022, new ArrayList<>(), "https://somewhere", new ArrayList<>(), new ArrayList<>());
        List<Movie> listOfMovies = new ArrayList<>();
        listOfMovies.add(movie);
        List<String> genres = movie.getGenres();
        genres.add("Action");

        when(movieRepository.findAll()).thenReturn(listOfMovies);

        List<String> listOfGenres = new ArrayList<>();
        listOfGenres.add("action");

        HashSet<Integer> list = movieService.getMoviesWith(listOfGenres);
        // HashSet<Integer> list2 = movieService.getMoviesWith(listOfGenres);
        assertEquals(1, list.size());
    }
}
