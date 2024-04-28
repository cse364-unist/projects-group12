package com.group12.rest2night.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {
    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    void testRandomQuiz() {

        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz()); // Add at least one quiz

        when(quizRepository.findAll()).thenReturn(quizzes);

        Random random = mock(Random.class);
        when(random.nextInt(quizzes.size())).thenReturn(0); // Always return 0 for predictable testing

        Quiz randomQuiz = quizService.randomQuiz();

        assertEquals(quizzes.get(0), randomQuiz);
    }
}
