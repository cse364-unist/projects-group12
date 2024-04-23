package com.group12.rest2night.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ratings")
public class Rating {
    @Id
    private ObjectId id;
    private int movieId;
    private int employeeId;
    private int rating;
    private int timestamp;
}
