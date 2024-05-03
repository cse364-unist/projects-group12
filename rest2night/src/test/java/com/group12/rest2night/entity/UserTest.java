package com.group12.rest2night.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;


public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        ObjectId id = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "testPassword";
        List<Integer> wishList = Arrays.asList(1, 2, 3);
        long points = 100;
        List<Integer> unlockedMovies = Arrays.asList(4, 5, 6);
        int age = 30;
        String occupation = "Engineer";
        String gender = "Male";

        User user = new User(id, userId, username, password, wishList, points, unlockedMovies, age, occupation, gender);

        assertEquals(id, user.getId());
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(wishList, user.getWishList());
        assertEquals(points, user.getPoints());
        assertEquals(unlockedMovies, user.getUnlockedMovies());
        assertEquals(age, user.getAge());
        assertEquals(occupation, user.getOccupation());
        assertEquals(gender, user.getGender());
    }

    @Test
    public void testUserSetters() {
        User user = new User();
        ObjectId id = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "testPassword";
        List<Integer> wishList = Arrays.asList(1, 2, 3);
        long points = 100;
        List<Integer> unlockedMovies = Arrays.asList(4, 5, 6);
        int age = 30;
        String occupation = "Engineer";
        String gender = "Male";

        user.setId(id);
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setWishList(wishList);
        user.setPoints(points);
        user.setUnlockedMovies(unlockedMovies);
        user.setAge(age);
        user.setOccupation(occupation);
        user.setGender(gender);

        assertEquals(id, user.getId());
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(wishList, user.getWishList());
        assertEquals(points, user.getPoints());
        assertEquals(unlockedMovies, user.getUnlockedMovies());
        assertEquals(age, user.getAge());
        assertEquals(occupation, user.getOccupation());
        assertEquals(gender, user.getGender());
    }

    @Test
    void testEqualsAndHashCode() {
        ObjectId someId = new ObjectId();

        User user1 = new User(someId, 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");
        User user2 = new User(someId, 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");

        User user3 = new User(someId, 123, "testUser1", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");
        User user4 = new User(someId, 123, "testUser1", "password2", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");

        User user5 = new User(someId, 123, "testUser2", "password", new ArrayList<>(), 100L, new ArrayList<>(), 26, "Engineer", "F");
        User user6 = new User(someId, 123, "testUser2", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");

        User user7 = new User(someId, 123, "testUser3", "password", new ArrayList<>(), 100L, new ArrayList<>(), 26, "Engineer", "F");
        User user8 = new User(someId, 123, "testUser3", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "M");

        User user9 = new User(someId, 123, "testUser4", "password", new ArrayList<>(), 900L, new ArrayList<>(), 26, "Engineer", "F");
        User user10 = new User(someId, 123, "testUser4", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "M");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setUsername("anotherUser");
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user3, user4);
        assertNotEquals(user3.hashCode(), user4.hashCode());
        assertNotEquals(user5, user6);
        assertNotEquals(user5.hashCode(), user6.hashCode());
        assertNotEquals(user7, user8);
        assertNotEquals(user7.hashCode(), user8.hashCode());
        assertNotEquals(user10, user9);
        assertNotEquals(user10.hashCode(), user9.hashCode());

        assertNotEquals(user1, null);

        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "testPassword";

        User user11 = new User(id1, userId, username, password, null, 0, null, 0, null, null);
        User user22 = new User(id1, userId, username, password, null, 0, null, 0, null, null);
        User user33 = new User(id2, userId, username, password, null, 0, null, 0, null, null);

        assertEquals(user11, user22);
        assertEquals(user11.hashCode(), user22.hashCode());
        assertEquals(user11.toString(), user22.toString());

        assertEquals(user11.hashCode(), user11.hashCode()); // Consistency check
        assertEquals(user11, user11); // Reflexivity check

        assertEquals(user11.equals(user33), false);
    }

    @Test
    void testToString() {
        User user = new User();

        assertNotNull(user.toString());
    }

    @Test
    void testEqualsWithDifferentValues() {
        User user1 = new User(new ObjectId(), 123, "testUser", "password", new ArrayList<>(), 100L, new ArrayList<>(), 25, "Engineer", "F");
        User user2 = new User(new ObjectId(), 456, "anotherUser", "password123", new ArrayList<>(), 200L, new ArrayList<>(), 30, "Doctor", "M");
        assertFalse(user1.equals(user2));
    }

    @Test
    public void testEmptyListsInConstructor() {
        ObjectId id = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "testPassword";

        User user = new User(id, userId, username, password, new ArrayList<>(), 0, new ArrayList<>(), 0, null, null);

        assertEquals(id, user.getId());
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(new ArrayList<>(), user.getWishList());
        assertEquals(0, user.getPoints());
        assertEquals(new ArrayList<>(), user.getUnlockedMovies());
        assertEquals(0, user.getAge());
        assertEquals(null, user.getOccupation());
        assertEquals(null, user.getGender());
    }

    @Test
    public void testNotEquals() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        int userId = 123;
        String username = "testUser";
        String password = "testPassword";

        User user1 = new User(id1, userId, username, password, null, 0, null, 0, null, null);
        User user2 = new User(id2, userId, username, password, null, 0, null, 0, null, null);

        assertEquals(user1.equals(user2), false);
    }
    
}
