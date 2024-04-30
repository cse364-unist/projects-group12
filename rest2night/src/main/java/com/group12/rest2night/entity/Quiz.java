package com.group12.rest2night.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quizzes")
public class Quiz {
    @Id
    private ObjectId id;
    private String question;
    private List<String> answers;
    private String answer;
}
