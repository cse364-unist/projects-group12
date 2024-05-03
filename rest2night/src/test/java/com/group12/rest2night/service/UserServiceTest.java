package com.group12.rest2night.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.repository.RatingRepository;
import com.group12.rest2night.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private UserService userService;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    void testAddMovieToWishlist() {

        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        userService.addMovieToWishlist("testUser", 1);

        assertTrue(user.getWishList().contains(1));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddMovieToWishlistWithExistedWL() {

        User user = new User();
        user.setUsername("testUser");
        user.setWishList(new ArrayList<>(0));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        userService.addMovieToWishlist("testUser", 1);

        assertTrue(user.getWishList().contains(1));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddMovieToWishlistExceptionHandler() {

        assertThrows(RuntimeException.class, () -> {userService.addMovieToWishlist(null, 1);});

    }


    @Test
    void testGetWishlist() {

        User user = new User();
        user.setUsername("testUser");
        user.setWishList(Arrays.asList(1, 2));

        Movie movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setTitle("Movie 1");

        Movie movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setTitle("Movie 2");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        when(movieService.findMovie(1)).thenReturn(Optional.of(movie1));
        when(movieService.findMovie(2)).thenReturn(Optional.of(movie2));

        List<Movie> wishlist = userService.getWishlist("testUser");

        assertEquals(2, wishlist.size());
        assertTrue(wishlist.stream().allMatch(movie -> movie.getTitle().startsWith("Movie")));
    }

    @Test
    void testGetWishlistExceptionHandler() {

        assertThrows(RuntimeException.class, () -> {userService.getWishlist(null);});
        
    }

    @Test
    void testRegister() {

        User user = new User();
        user.setUsername("testUser");

        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
    }

    @Test
    void testIsValidUser() {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        assertTrue(userService.isValidUser("testUser", "password"));

        assertFalse(userService.isValidUser("testUser", "wrongPassword"));

        assertFalse(userService.isValidUser("noUserTest", "noPassword"));
    }

    @Test
    void testIsUserExists() {

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new User()));

        assertTrue(userService.isUserExists("testUser"));

        assertFalse(userService.isUserExists("nonExistingUser"));
    }

    @Test
    void testGetUsersWith(){
        List<User> listOfUsers = new ArrayList<>();
        User user = new User(new ObjectId(), 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");
        listOfUsers.add(user);

        when(userRepository.findAll()).thenReturn(listOfUsers);

        ArrayList<Integer> list =  userService.getUsersWith("engineer", 25, "f");
        ArrayList<Integer> list2 =  userService.getUsersWith("programmer", 25, "f");
        ArrayList<Integer> list3 =  userService.getUsersWith("engineer", 25, "m");
        ArrayList<Integer> list4 =  userService.getUsersWith("engineer", 56, "f");
        ArrayList<Integer> list5 =  userService.getUsersWith("", -1, "");

        assertEquals(1, list.size());
        assertEquals(0, list3.size());
        assertEquals(0, list2.size());
        assertEquals(0, list4.size());
        assertEquals(1, list5.size());
    }

    @Test
    void testGetRatingsOfUser(){
        Rating rating = new Rating(new ObjectId(), 5, 5, 5, 1618548376);
        List<Rating> listOfRatings = new ArrayList<>();
        listOfRatings.add(rating);

        when(ratingRepository.findByUserId(0)).thenReturn(listOfRatings);

        List<Rating> list = userService.getRatingsOfUser(0);

        assertEquals(1, list.size());
    }
}
