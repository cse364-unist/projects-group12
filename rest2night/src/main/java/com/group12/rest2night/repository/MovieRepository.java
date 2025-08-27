package com.group12.rest2night.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.group12.rest2night.entity.Movie;


@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId>{
    Optional<Movie> findByMovieId(int movieId);

    @Query("{ 'genres': { $in: ?0 } }")
    List<Movie> findByGenresIn(List<String> genres);
}
