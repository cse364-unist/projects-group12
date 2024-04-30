package com.group12.rest2night.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    void testSettersAndGetters() {
        Comment comment = new Comment();

        String username = "testUser";
        LocalDateTime timestamp = LocalDateTime.now();
        String body = "The test comment.";
        int rate = 5;

        comment.setUsername(username);
        comment.setTimestamp(timestamp);
        comment.setBody(body);
        comment.setRate(rate);

        assertEquals(username, comment.getUsername());
        assertEquals(timestamp, comment.getTimestamp());
        assertEquals(body, comment.getBody());
        assertEquals(rate, comment.getRate());
    }
    

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime timeNow = LocalDateTime.now();
        Comment comment1 = new Comment("user1", timeNow, "Body", 5);
        Comment comment2 = new Comment("user1", timeNow, "Body", 5);

        assertEquals(comment1, comment2);
        assertEquals(comment1.hashCode(), comment2.hashCode());

        comment2.setRate(4);

        assertNotEquals(comment1, comment2);
        assertNotEquals(comment1.hashCode(), comment2.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime timeNow = LocalDateTime.now();
        Comment comment = new Comment("user1", timeNow, "Body", 5);

        assertEquals("Comment(username=user1, timestamp=" + timeNow + ", body=Body, rate=5)",comment.toString());
    }
}
