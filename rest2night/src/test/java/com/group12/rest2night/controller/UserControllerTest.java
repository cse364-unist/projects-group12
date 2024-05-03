package com.group12.rest2night.controller;

import com.group12.rest2night.entity.LoginRequest;
import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.User;
import com.group12.rest2night.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testAddToWishlist() {
        String username = "testuser";
        int movieId = 123;

        ResponseEntity<String> response = userController.addToWishlist(username, movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie added to wishlist successfully", response.getBody());
    }


    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password"); // (Note: Avoid plain text passwords in real applications)
        when(userService.isUserExists(user.getUsername())).thenReturn(false); // User doesn't exist yet

        ResponseEntity<String> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }
//
    @Test
    void testAddUserExistingUsername() {
        User user = new User();
        user.setUsername("existinguser");
        when(userService.isUserExists(user.getUsername())).thenReturn(true);

        ResponseEntity<String> response = userController.addUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", response.getBody());
    }
//

}
