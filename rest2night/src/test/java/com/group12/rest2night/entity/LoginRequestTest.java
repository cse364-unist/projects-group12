package com.group12.rest2night.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {
    @Test
    void testSettersAndGetters() {
        LoginRequest request = new LoginRequest();

        String username = "testUser";
        String password = "password";

        request.setUsername(username);
        request.setPassword(password);

        assertEquals(username, request.getUsername());
        assertEquals(password, request.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginRequest request1 = new LoginRequest();
        request1.setUsername("testUser");
        request1.setPassword("password");

        LoginRequest request2 = new LoginRequest();
        request2.setUsername("testUser");
        request2.setPassword("password");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        request2.setPassword("anotherPassword");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        assertNotEquals(request1, null);
    }

    @Test
    void testToString() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testUser");
        request.setPassword("password");

        assertNotNull(request.toString());
    }
}

