package com.group12.rest2night.entity;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizTest {

    @Test
    public void testQuizConstructorAndGetters() {
        ObjectId id = new ObjectId();
        String question = "What is the capital of France?";
        List<String> answers = Arrays.asList("Paris", "London", "Berlin", "Rome");
        String answer = "Paris";

        Quiz quiz = new Quiz(id, question, answers, answer);

        assertEquals(id, quiz.getId());
        assertEquals(question, quiz.getQuestion());
        assertEquals(answers, quiz.getAnswers());
        assertEquals(answer, quiz.getAnswer());
    }

    @Test
    public void testQuizSetters() {
        Quiz quiz = new Quiz();
        ObjectId id = new ObjectId();
        String question = "What is the capital of France?";
        List<String> answers = Arrays.asList("Paris", "London", "Berlin", "Rome");
        String answer = "Paris";

        quiz.setId(id);
        quiz.setQuestion(question);
        quiz.setAnswers(answers);
        quiz.setAnswer(answer);

        assertEquals(id, quiz.getId());
        assertEquals(question, quiz.getQuestion());
        assertEquals(answers, quiz.getAnswers());
        assertEquals(answer, quiz.getAnswer());
    }

    @Test
    public void testEqualsAndHashCode() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        String question1 = "Question 1";
        String question2 = "Question 2";
        List<String> answers1 = Arrays.asList("A", "B", "C");
        List<String> answers2 = Arrays.asList("X", "Y", "Z");
        String answer1 = "A";
        String answer2 = "X";

        Quiz quiz1 = new Quiz(id1, question1, answers1, answer1);
        Quiz quiz2 = new Quiz(id1, question1, answers1, answer1);
        Quiz quiz3 = new Quiz(id2, question1, answers1, answer1);
        Quiz quiz4 = new Quiz(id1, question2, answers1, answer1);
        Quiz quiz5 = new Quiz(id1, question1, answers2, answer1);
        Quiz quiz6 = new Quiz(id1, question1, answers1, answer2);

        assertEquals(quiz1, quiz2);
        assertEquals(quiz1.hashCode(), quiz2.hashCode());
        assertEquals(quiz1.toString(), quiz2.toString());

        assertEquals(quiz1.hashCode(), quiz1.hashCode()); // Consistency check
        assertEquals(quiz1, quiz1); // Reflexivity check

        assertEquals(quiz1.equals(quiz3), false);
        assertEquals(quiz1.equals(quiz4), false);
        assertEquals(quiz1.equals(quiz5), false);
        assertEquals(quiz1.equals(quiz6), false);
    }

    @Test
    public void testNotEquals() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        String question1 = "Question 1";
        String question2 = "Question 2";
        List<String> answers1 = Arrays.asList("A", "B", "C");
        List<String> answers2 = Arrays.asList("X", "Y", "Z");
        String answer1 = "A";
        String answer2 = "X";

        Quiz quiz1 = new Quiz(id1, question1, answers1, answer1);
        Quiz quiz2 = new Quiz(id2, question2, answers2, answer2);

        assertEquals(quiz1.equals(quiz2), false);
    }
}
