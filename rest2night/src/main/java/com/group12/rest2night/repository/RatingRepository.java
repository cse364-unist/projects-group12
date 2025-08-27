package com.group12.rest2night.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.group12.rest2night.entity.Rating;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, ObjectId>{
    List<Rating> findByMovieId(int movieId);
    List<Rating> findByUserId(int userId);

    @Query("{ 'userId': { $in: ?0}, 'movieId': { $in: ?1} }")
    List<Rating> findByUserIdAndMovieIdIn(List<Integer> userIds, List<Integer> movieIds);
}
