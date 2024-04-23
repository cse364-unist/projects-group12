package com.group12.rest2night.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.group12.rest2night.entity.User;


public interface UserRepository extends MongoRepository<User, ObjectId> {

}

