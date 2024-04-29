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
        quizzes.add(new Quiz());

        when(quizRepository.findAll()).thenReturn(quizzes);

        Quiz randomQuiz = quizService.randomQuiz();

        assertEquals(quizzes.get(0), randomQuiz);
    }
}
