package com.group12.rest2night.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class QuizTest {
    @Test
    void testSettersAndGetters() {
        Quiz quiz = new Quiz();

        ObjectId id = new ObjectId();
        String question = "What is 2 + 2?";
        List<String> answers = new ArrayList<>();
        answers.add("3");
        answers.add("4");
        String correctAnswer = "4";

        quiz.setId(id);
        quiz.setQuestion(question);
        quiz.setAnswers(answers);
        quiz.setAnswer(correctAnswer);

        // Check getters
        assertEquals(id, quiz.getId());
        assertEquals(question, quiz.getQuestion());
        assertEquals(answers, quiz.getAnswers());
        assertEquals(correctAnswer, quiz.getAnswer());
    }

    @Test
    void testEqualsAndHashCode() {
        ObjectId someId = new ObjectId();

        Quiz quiz1 = new Quiz(someId, "What is 2 + 2?", List.of("3", "4"), "4");
        Quiz quiz2 = new Quiz(someId, "What is 2 + 2?", List.of("3", "4"), "4");

        assertEquals(quiz1, quiz2);
        assertEquals(quiz1.hashCode(), quiz2.hashCode());

        quiz2.setAnswer("3");

        assertNotEquals(quiz1, quiz2);
        assertNotEquals(quiz1.hashCode(), quiz2.hashCode());

        assertNotEquals(quiz1, null);
    }

    @Test
    void testToString() {
        Quiz quiz = new Quiz();

        assertNotNull(quiz.toString());
    }
}
