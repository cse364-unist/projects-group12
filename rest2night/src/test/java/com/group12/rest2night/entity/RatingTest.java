package com.group12.rest2night.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    void testEqualsAndHashCode() {

        ObjectId someId = new ObjectId();
        Rating rating1 = new Rating(someId, 123, 456, 5, 1618548376);
        Rating rating2 = new Rating(someId, 123, 456, 5, 1618548376);

        assertEquals(rating1, rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());

        rating2.setRating(4);

        assertNotEquals(rating1, rating2);
        assertNotEquals(rating1.hashCode(), rating2.hashCode());

        assertNotEquals(rating1, null);
    }

    @Test
    void testToString() {
        Rating rating = new Rating();

        assertNotNull(rating.toString());
    }
}
