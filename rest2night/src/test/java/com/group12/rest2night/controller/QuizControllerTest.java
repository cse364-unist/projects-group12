package com.group12.rest2night.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.service.QuizService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @Test
    void testGetQuiz() {
        // Setup
        Quiz expectedQuiz = new Quiz(); // Create a sample Quiz object.
        when(quizService.randomQuiz()).thenReturn(expectedQuiz);

        // Execution
        ResponseEntity<Quiz> response = quizController.getQuiz();

        // Assertions (Oracles)
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check HTTP status
        assertNotNull(response.getBody()); // Ensure `Quiz` isn't null
        assertEquals(expectedQuiz, response.getBody()); // Verify correct `Quiz` object
    }
}
