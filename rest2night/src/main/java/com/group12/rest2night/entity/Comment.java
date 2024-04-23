package com.group12.rest2night.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private ObjectId userId;
    private LocalDateTime timestamp;
    private String body;
    private int rate;
}
