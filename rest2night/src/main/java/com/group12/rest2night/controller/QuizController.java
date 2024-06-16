package com.group12.rest2night.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.service.QuizService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<Quiz> getQuiz() {
        return new ResponseEntity<>(quizService.randomQuiz(), HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, String> request) {
        String quizId = request.get("quizId");
        String selectedAnswer = request.get("selectedAnswer");

        boolean isCorrect = quizService.checkAnswer(quizId, selectedAnswer);
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("nextQuiz", quizService.randomQuiz());

        // Logging for debugging
        System.out.println("Quiz ID: " + quizId);
        System.out.println("Selected Answer: " + selectedAnswer);
        System.out.println("Is Correct: " + isCorrect);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}