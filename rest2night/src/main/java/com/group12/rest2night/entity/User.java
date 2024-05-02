package com.group12.rest2night.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    private int userId;
    private String username;
    private String password;
    private List<Integer> wishList;
    private long points;
    private List<Integer> unlockedMovies;
    private int age;
    private String occupation;
    private String gender;
}
