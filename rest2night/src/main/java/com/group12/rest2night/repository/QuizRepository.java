package com.group12.rest2night.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.group12.rest2night.entity.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, ObjectId>{
    
}
