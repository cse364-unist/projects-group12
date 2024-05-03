package com.group12.rest2night.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginRequestTest {

    @Test
    public void testLoginRequestConstructorAndGetters() {
        String username = "testUser";
        String password = "testPassword";

        LoginRequest loginRequest = new LoginRequest(username, password);

        assertEquals(username, loginRequest.getUsername());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    public void testLoginRequestSetters() {
        LoginRequest loginRequest = new LoginRequest();
        String username = "testUser";
        String password = "testPassword";

        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        assertEquals(username, loginRequest.getUsername());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        String username1 = "testUser1";
        String username2 = "testUser2";
        String password1 = "testPassword1";
        String password2 = "testPassword2";

        LoginRequest loginRequest1 = new LoginRequest(username1, password1);
        LoginRequest loginRequest2 = new LoginRequest(username1, password1);
        LoginRequest loginRequest3 = new LoginRequest(username2, password1);
        LoginRequest loginRequest4 = new LoginRequest(username1, password2);

        assertEquals(loginRequest1, loginRequest2);
        assertEquals(loginRequest1.hashCode(), loginRequest2.hashCode());
        assertEquals(loginRequest1.toString(), loginRequest2.toString());

        assertEquals(loginRequest1.hashCode(), loginRequest1.hashCode()); // Consistency check
        assertEquals(loginRequest1, loginRequest1); // Reflexivity check

        assertEquals(loginRequest1.equals(loginRequest3), false);
        assertEquals(loginRequest1.equals(loginRequest4), false);
    }

    @Test
    public void testNotEquals() {
        String username1 = "testUser1";
        String username2 = "testUser2";
        String password1 = "testPassword1";
        String password2 = "testPassword2";

        LoginRequest loginRequest1 = new LoginRequest(username1, password1);
        LoginRequest loginRequest2 = new LoginRequest(username2, password2);

        assertEquals(loginRequest1.equals(loginRequest2), false);
    }
}
