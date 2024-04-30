package com.group12.rest2night.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void testSettersAndGetters() {
        User user = new User();

        ObjectId id = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "password";
        List<Integer> wishList = new ArrayList<>();
        wishList.add(1);
        long points = 100L;
        List<Integer> unlockedMovies = new ArrayList<>();
        unlockedMovies.add(2);
        int age = 25;
        String occupation = "Engineer";

        user.setId(id);
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setWishList(wishList);
        user.setPoints(points);
        user.setUnlockedMovies(unlockedMovies);
        user.setAge(age);
        user.setOccupation(occupation);

        assertEquals(id, user.getId());
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(wishList, user.getWishList());
        assertEquals(points, user.getPoints());
        assertEquals(unlockedMovies, user.getUnlockedMovies());
        assertEquals(age, user.getAge());
        assertEquals(occupation, user.getOccupation());
    }

    @Test
    void testEqualsAndHashCode() {
        ObjectId someId = new ObjectId();

        User user1 = new User(someId, 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer");
        User user2 = new User(someId, 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setUsername("anotherUser");

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(user1, null);
    }

    @Test
    void testToString() {
        User user = new User();

        assertNotNull(user.toString());
    }
}
