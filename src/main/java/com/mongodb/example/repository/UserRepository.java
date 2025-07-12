package com.mongodb.example.repository;

import com.mongodb.example.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByIdGreaterThan(ObjectId id);
}
