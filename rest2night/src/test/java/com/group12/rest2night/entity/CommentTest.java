package com.group12.rest2night.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CommentTest {

    @Test
    public void testCommentConstructorAndGetters() {
        String username = "testUser";
        LocalDateTime timestamp = LocalDateTime.now();
        String body = "Test comment body";
        int rate = 5;

        Comment comment = new Comment(username, timestamp, body, rate);

        assertEquals(username, comment.getUsername());
        assertEquals(timestamp, comment.getTimestamp());
        assertEquals(body, comment.getBody());
        assertEquals(rate, comment.getRate());
    }

    @Test
    public void testCommentSetters() {
        Comment comment = new Comment();
        String username = "testUser";
        LocalDateTime timestamp = LocalDateTime.now();
        String body = "Test comment body";
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
    public void testEqualsAndHashCode() {
        String username1 = "testUser1";
        String username2 = "testUser2";
        LocalDateTime timestamp1 = LocalDateTime.now();
        LocalDateTime timestamp2 = LocalDateTime.now().plusHours(1);
        String body1 = "Test comment body 1";
        String body2 = "Test comment body 2";
        int rate1 = 5;
        int rate2 = 4;

        Comment comment1 = new Comment(username1, timestamp1, body1, rate1);
        Comment comment2 = new Comment(username1, timestamp1, body1, rate1);
        Comment comment3 = new Comment(username2, timestamp1, body1, rate1);
        Comment comment4 = new Comment(username1, timestamp2, body1, rate1);
        Comment comment5 = new Comment(username1, timestamp1, body2, rate1);
        Comment comment6 = new Comment(username1, timestamp1, body1, rate2);

        assertEquals(comment1, comment2);
        assertEquals(comment1.hashCode(), comment2.hashCode());
        assertEquals(comment1.toString(), comment2.toString());

        assertEquals(comment1.hashCode(), comment1.hashCode()); // Consistency check
        assertEquals(comment1, comment1); // Reflexivity check

        assertEquals(comment1.equals(comment3), false);
        assertEquals(comment1.equals(comment4), false);
        assertEquals(comment1.equals(comment5), false);
        assertEquals(comment1.equals(comment6), false);
    }

    @Test
    public void testNotEquals() {
        String username1 = "testUser1";
        String username2 = "testUser2";
        LocalDateTime timestamp1 = LocalDateTime.now();
        LocalDateTime timestamp2 = LocalDateTime.now().plusHours(1);
        String body1 = "Test comment body 1";
        String body2 = "Test comment body 2";
        int rate1 = 5;
        int rate2 = 4;

        Comment comment1 = new Comment(username1, timestamp1, body1, rate1);
        Comment comment2 = new Comment(username2, timestamp2, body2, rate2);

        assertEquals(comment1.equals(comment2), false);
    }
}
