package com.group12.rest2night.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.repository.QuizRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group12.rest2night.entity.Quiz;
import com.group12.rest2night.repository.QuizRepository;
import org.bson.types.ObjectId;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz randomQuiz() {
        List<Quiz> quizzes = quizRepository.findAll();
        Random random = new Random();
        int randomIndex = random.nextInt(quizzes.size());
        return quizzes.get(randomIndex);
    }

}