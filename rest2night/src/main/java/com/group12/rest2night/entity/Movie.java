package com.group12.rest2night.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {
    private ObjectId id;
    private int movieId;
    private String imdbId;
    private String title;
    private int year;
    private List<String> genres;
    private String trailerLine;
    private double rate;
    private List<Comment> comments;
}
