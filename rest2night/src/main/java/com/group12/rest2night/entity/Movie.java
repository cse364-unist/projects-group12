package com.group12.rest2night.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private ObjectId id;
    private int movieId;
    private String title;
    private int year;
    private List<String> genres;
    private String posterLink;
    private ArrayList<Double> rate;
    private List<Comment> comments;
    //not included yet, but will be//!!
    //private int price;
}
