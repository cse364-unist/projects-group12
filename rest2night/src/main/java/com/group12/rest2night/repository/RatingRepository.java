package com.group12.rest2night.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.group12.rest2night.entity.Rating;
import java.util.List;


public interface RatingRepository extends MongoRepository<Rating, ObjectId>{
    List<Rating> findByMovieId(int movieId);
}
